package br.com.siab.app;

import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.sun.javafx.application.LauncherImpl;

import br.com.siab.app.Main;
import br.com.siab.app.SplashScreenLoader;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.application.Preloader;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

@SuppressWarnings("restriction")
public class Main extends Application{

	public static Stage stage;
	public static EntityManagerFactory factory;

	private static final int COUNT_LIMIT = 10;

	@Override
	public void start(Stage primaryStage) throws Exception {
		TimeZone.setDefault(TimeZone.getTimeZone("Etc/UTC"));

		Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/br/com/siab/view/MainTela.fxml")));
		Platform.setImplicitExit(false);
		primaryStage.setMaximized(true);
		primaryStage.setScene(scene);
		primaryStage.initStyle(StageStyle.UNDECORATED);
		primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("")));
		primaryStage.show();

		stage = primaryStage;

		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
		    @Override
		    public void handle(WindowEvent event) {
		        event.consume();
		    }
		});

		primaryStage.iconifiedProperty().addListener(new ChangeListener<Boolean>() {

			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(oldValue){
					primaryStage.setFullScreen(true);
					primaryStage.setFullScreen(false);
				}

			}
		});



	}

	@Override
	public void init() throws Exception {

		factory = Persistence.createEntityManagerFactory("SIAB");


		for (int i = 0; i < COUNT_LIMIT; i++) {
			double progress = (double) i/10;
			LauncherImpl.notifyPreloader(this, new Preloader.ProgressNotification(progress));
		}
	}

	public static void main(String[] args) {
		LauncherImpl.launchApplication(Main.class, SplashScreenLoader.class, args);
	}

}
