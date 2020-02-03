package jwoo.apps.GSpyMonitor;
	
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Font;


public class Main extends Application {
	
	private Map<String, String> Forms = new HashMap<String, String>();
	
	@Override
	public void init()
	{
		Forms.put("ServerConnection", "view/ServerConnection.fxml");
		Forms.put("SignIn", "view/SignIn.fxml");
		Forms.put("DeviceOverview", "view/DeviceOverview");
		Forms.put("DeviceDetails", "view/DeviceDetails");
	}
	
	@Override
	public void start(Stage primaryStage)
	{
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("GSpyMonitor v0.0.1");
		
		initRootLayout();
		
		showSignIn();
	}
	
	private void initRootLayout()
	{
		try
		{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("view/RootLayout.fxml"));
			rootLayout = loader.load();
			
			Scene scene = new Scene(rootLayout);
			
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			
			primaryStage.sizeToScene();
			primaryStage.show();
		}
		
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	private void showForm(String formID)
	{
		String ResourceURI = Forms.get(formID);
		
		if (ResourceURI == null)
		{
			System.err.printf("Failed to show form %s: Invalid formID\n", formID);
		}
		
		else
		{
			try
			{
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(Main.class.getResource(Forms.get(formID)));
				AnchorPane SignIn = loader.load();
			
				rootLayout.setCenter(SignIn);
				rootLayout.autosize();
			}
		
			catch (IOException e)
			{
				e.printStackTrace();
			}	
		}		
	}
	
	public static void main(String[] args)
	{
		launch(args);
	}
	
	public Stage getPrimaryStage()
	{
		return primaryStage;
	}
	
	private Stage primaryStage;
	private BorderPane rootLayout;
}
