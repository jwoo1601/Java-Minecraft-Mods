package jw.minecraft.utility.transformers;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static jw.minecraft.utility.LogHelper.error;
import static jw.minecraft.utility.LogHelper.info;

import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import jw.minecraft.utility.ImprovedMinecraftUtility;
import jw.minecraft.utility.callback.Callback;
import jw.minecraft.utility.callback.CallbackType;
import jw.minecraft.utility.callback.ErrorCallback;
import jw.minecraft.utility.catchable.ErrorBase;
import jw.minecraft.utility.command.AddonCommand;
import jw.minecraft.utility.command.CommandData;
import jw.minecraft.utility.command.CommandOutput;
import jw.minecraft.utility.command.IExecutable;
import jw.minecraft.utility.command.IOutputHandler;
import jw.minecraft.utility.command.SubCommand;
import jw.minecraft.utility.localization.Parser;
import jw.minecraft.utility.localization.Parser.ParseType;
import jw.minecraft.utility.permissions.IPermission;
import jw.minecraft.utility.permissions.Permissions;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;

public class CommandTransformer extends AbstractTransformer<Class, CommandData> {
	
	private Object commandInstance;
	
	private AddonCommand rawCache;
	
	private final Sub SubTransformer;
	
	private List<Method> methodCache;
	
	private IPermission[] permissionCache;
	
	private ErrorCallback callbackCache;
	
	private IOutputHandler handlerCache;

	
	public CommandTransformer() {
		SubTransformer = new Sub();
	}
	
	private void init() {
		raw = null;
		commandInstance = null;
		rawCache = null;
		methodCache = null;
		permissionCache = null;
		callbackCache = null;
		handlerCache = null;
	}
	
	@Override
	public CommandData transform(@Nonnull Class addonCommand) {
		init();
		this.raw = checkNotNull(addonCommand, "addonCommand must not be null!");
		validate();
		

		String parsedName = Parser.parse(rawCache.name(), ParseType.TRANSLATE);
		String parsedUsage = Parser.parse(rawCache.usage(), ParseType.TRANSLATE);
		
		Parser.Arguments = new String[1];
		Parser.Arguments[0] = rawCache.addonId();
		
		CommandData.Sub[] subs = new CommandData.Sub[methodCache.size()];
		for (int k=0; k < subs.length; k++)
			subs[k] = SubTransformer.transform(methodCache.get(k));
		
		Field[] fields = raw.getDeclaredFields();
		for (Field f : fields) {
			
			AddonCommand.OutputHandler outhandler = f.getAnnotation(AddonCommand.OutputHandler.class);
			if (outhandler != null) {
				f.setAccessible(true);
				handlerCache = CommandOutput.getHandler(Parser.parse(outhandler.prefix(), ParseType.TRANSLATE));
				
				try { f.set(commandInstance, handlerCache); }
				catch (IllegalArgumentException e) { error(e.getMessage()); }
				catch (IllegalAccessException e) { error(e.getMessage()); }
			}
		}
		
		return new CommandData(commandInstance, parsedName, parsedUsage, permissionCache, subs, callbackCache, handlerCache);
	}

	@Override
	protected void validate() {
		Annotation annotation = raw.getDeclaredAnnotation(AddonCommand.class);
		checkNotNull(annotation, "target class must be annotated with @AddonCommand!");
		rawCache = (AddonCommand) annotation;
		
		String idOp = Permissions.getInstance().OP.getId();
		String idAdmin = Permissions.getInstance().ADMIN.getId();
		String idDefault = Permissions.getInstance().DEFAULT.getId();
		
		String[] rp = rawCache.permissions();
		permissionCache = new IPermission[rp.length];
		for (int i=0; i < rp.length; i++) {
			if (rp[i].equals(idOp))
				permissionCache[i] = Permissions.getInstance().OP;
			else if (rp[i].equals(idAdmin))
				permissionCache[i] = Permissions.getInstance().ADMIN;
			else if (rp[i].equals(idDefault))
				permissionCache[i] = Permissions.getInstance().DEFAULT;
			else
				throw new IllegalArgumentException("id is not valid Permission Id!");
		}
		
		methodCache = Lists.newArrayList();
		for (Method m : raw.getDeclaredMethods()) {
			
			if (m.getDeclaredAnnotation(SubCommand.class) != null)
				methodCache.add(m);
		}
		
		checkArgument(methodCache.size() > 0, "target class must have at least a member method annotated");
		
		for (Method M : raw.getDeclaredMethods()) {
			Callback c = M.getAnnotation(Callback.class);
			
			if (c != null && c.type() == CallbackType.ERROR) {
				Class[] t = M.getParameterTypes();
				
				checkArgument(t.length == 1 && t[0].isAssignableFrom(ErrorBase.class), "ErrorCallback Method does not have appropriate Parameters!");
				
				try {
					commandInstance = raw.newInstance();
					callbackCache = new ErrorCallback(commandInstance, M);
				}				
				
				catch (InstantiationException e) { error(e.getMessage()); }
				catch (IllegalAccessException e) { error(e.getMessage()); }
			}
		}
		
	}
	
	
	class Sub extends AbstractTransformer<Method, CommandData.Sub> {
		
		private SubCommand subRawCache;
		
		private void init() {
			raw = null;
			subRawCache = null;
		}
		
		@Override
		public jw.minecraft.utility.command.CommandData.Sub transform(@Nonnull Method subCommand) {
			init();
			raw = checkNotNull(subCommand, "subCommand must not be null!");
			validate();
			
			CommandBody target = new CommandBody(commandInstance, subCommand);

			String parsedUsage = Parser.parse(subRawCache.usage(), ParseType.TRANSLATE, ParseType.SUBCOMMAND);
			String parsedDesc = Parser.parse(subRawCache.desc(), ParseType.TRANSLATE);
			
			return new CommandData.Sub(target, parsedUsage, subRawCache.minArgsLength(), subRawCache.maxArgsLength(), parsedDesc, subRawCache.allowNonPlayerSender(), callbackCache);
		}

		@Override
		protected void validate() {
			subRawCache = raw.getDeclaredAnnotation(SubCommand.class);
			checkNotNull(subRawCache, "target does not have @SubCommand annotation!");
			
			Class[] types = raw.getParameterTypes();
			checkArgument(types.length == 2 && ICommandSender.class.isAssignableFrom(types[0]) && String[].class.isAssignableFrom(String[].class), "target does not have appropriate Parameters!");
		}
		
		
		class CommandBody implements IExecutable {
			
			final Object Instance;
			
			final Method Target;
			
			CommandBody(Object instance, Method target) {
				checkArgument(target.getDeclaringClass().isInstance(instance), "instance is not a Type which has target as a Member Method!");
				Instance = instance;
				Target = target;
				Target.setAccessible(true);
			}

			@Override
			public void execute(ICommandSender executor, String[] args)	throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
				Target.invoke(Instance, executor, args);
			}
			
		}
	}
	
}
