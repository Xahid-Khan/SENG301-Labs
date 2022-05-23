package ronsapp;

import java.net.*;
import javax.swing.*;
import java.util.*;

public class RonsMusicCompanion {
	ImageComponent imageComponent;
	JFrame frame = new JFrame("Album Cover Viewer");
	JMenuBar menuBar;
	JMenu menu;
	// Need thread safe collection so not using HashMap
	Hashtable<String, String> albums = new Hashtable<String, String>();

	public static void main(String[] args) throws Exception {
		RonsMusicCompanion testDrive = new RonsMusicCompanion();
	}

	public RonsMusicCompanion() throws Exception {
		albums.put("Bromberg Plays Hendrix",
				"https://m.media-amazon.com/images/I/61KQpnoIMpL._SX522_.jpg");
		albums.put("Live at Leeds", "https://m.media-amazon.com/images/I/61oYeMBQmML._SX522_.jpg");
		albums.put("Violin Concerto", "https://m.media-amazon.com/images/I/912WwbttILL._SX522_.jpg");
		albums.put("Marshall Tucker Band", "https://m.media-amazon.com/images/I/71bgoFj9QIL._SX522_.jpg");
		albums.put("Kind of Blue", "https://m.media-amazon.com/images/I/71dQKN2UEfL._SX522_.jpg");
		albums.put("Scared to Dance", "https://m.media-amazon.com/images/I/71jrzX1tQvL._SX522_.jpg");

		URL initialURL = new URL((String)albums.get("Marshall Tucker Band"));

		menuBar = new JMenuBar();
		menu = new JMenu("Favorite Albums");
		menuBar.add(menu);
		frame.setJMenuBar(menuBar);

		for (Enumeration<String> e = albums.keys(); e.hasMoreElements();) {
			String name = (String) e.nextElement();
			JMenuItem menuItem = new JMenuItem(name);
			menu.add(menuItem);
			menuItem.addActionListener(event -> {
				imageComponent.setIcon(new TemporaryImageIcon(getAlbumUrl(event.getActionCommand())));
				frame.repaint();
			});
		}

		// set up frame and menus
		Icon icon = new TemporaryImageIcon(initialURL);
		imageComponent = new ImageComponent(icon);
		frame.getContentPane().add(imageComponent);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800, 650);
		
		frame.setVisible(true);
	}

	URL getAlbumUrl(String name) {
		try {
			return new URL((String) albums.get(name));
		} catch (MalformedURLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
