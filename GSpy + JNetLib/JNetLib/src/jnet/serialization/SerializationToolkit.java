package jnet.serialization;

import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Deque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import io.netty.buffer.AbstractByteBuf;
import io.netty.buffer.ByteBuf;
import jnet.serialization.serializer.Serializer;
import jnet.util.IUndoToolkit;
import jnet.util.Operation;
import jnet.util.ToolkitOperationException;

public class SerializationToolkit implements IUndoToolkit
{
	public SerializationToolkit(ByteBuf buffer)
	{		
		this(buffer, SerializationOptions.empty());
	}
	
	public SerializationToolkit(ByteBuf buffer, SerializationOptions options)
	{
		assert buffer != null;
		
		this.buffer = buffer;
		this.options = options;
		this.nodes = new LinkedList<>();
	}
	
	public SerializationToolkit node()
	{
		editedNode = new SNode(SNode.NO_HEADER);
		
		return this;
	}
	
	public SerializationToolkit node(int header)
	{
		editedNode = new SNode(header);
		
		return this;
	}
	
	public SerializationToolkit close() throws SerializationException
	{
		if (editedNode == null)
		{
			throw new SerializationException("No currently edited node to complete!");
		}
		
		else
		{
			editedNode.close();
		}
		
		return this;
	}
	
	public SNode get()
	{
		return editedNode;
	}
	
	public SerializationToolkit push() throws SerializationException
	{
		if (editedNode == null)
		{
			throw new SerializationException("No currently edited node to push into the stack!");
		}
		
		else
		{
			nodes.push(editedNode);
			editedNode = null;
		}
		
		return this;
	}
	
	public SerializationToolkit closeAndPush() throws SerializationException
	{		
		return close().push();
	}
	
	public SNode pop()
	{
		return nodes.pop();
	}
	
	public SerializationToolkit flush()
	{
		if (!nodes.isEmpty())
		{
			SNode target = nodes.pop();			
			while (target != null)
			{
				target.writeTo(buffer);
			}
		}
		
		return this;
	}
	
	/**
	 * Defers serialization of the incoming object to another serializer
	 * @param obj
	 */
	public void addObject(Object obj)
	{
		Class<?> serializerClass = SerializationManager.get(obj);
		Object serializer =  serializerClass.createInstance();
		Method serializerMethod = Arrays.stream(serializerClass.getMethods()).filter(m -> m.isAnnotationPresent(Serializer.class)).findFirst().get();
		
		if (serializerMethod == null)
		{
			// throw new SerializationException();
		}
		
		else
		{
			serializerMethod.invoke(serializer, obj, this);
		}
	}
	
	public void addByte(int data)
	{
		buffer.writeByte(data);
	}
	
	public void writeBytes(byte[] data)
	{
		buffer.writeBytes(data);
	}
	
	public void writeBytes(byte[] data, int length)
	{
		buffer.writeBytes(data, 0, length);
	}
	
	public void writeBytes(byte[] data, int offset, int length)
	{
		buffer.writeBytes(data, offset, length);
	}
	
	public void writeBoolean(boolean data)
	{
		buffer.writeBoolean(data);
	}
	
	public void writeChar(int data)
	{
		buffer.writeChar(data);
	}
	
	public void writeShort(short data)
	{
		
	}
	
	public void writeInt(int data)
	{
		
	}
	
	public void writeLong(long data)
	{
		
	}
	
	public void addFloat(float data)
	{
		
	}
	
	public void addDouble(double data)
	{
		
	}
	
	public void addString(String data)
	{
		
	}
	
	public void addString(String data, Charset charset)
	{
		
	}
	
	public SerializationOptions options()
	{
		return options;
	}
	
	private SerializationOptions options;
	private ByteBuf buffer;
	private Stack<Operation<OpType, ByteBuf>> markers;

	@Override
	public void undo() throws ToolkitOperationException
	{
		int markedIndex = markers.pop();
		buffer.writerIndex(markedIndex);
	}

	@Override
	public void redo() throws ToolkitOperationException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void rewind(int count) throws ToolkitOperationException {
		// TODO Auto-generated method stub
		
	}
	
	private static enum OpType
	{
		ADD
	}
}
