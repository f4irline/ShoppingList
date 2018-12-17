package com.github.f4irline.app.containers;

import javafx.scene.layout.VBox;
import javafx.scene.control.ScrollPane;

/**
 * A container for the shopping list application's main content: the shopping list items.
 * 
 * @author Tommi Lepola
 * @version 1.0
 * @since 2018.1216
 */
public class Content extends ScrollPane {

    /**
     * Initializes the content scrollpane by setting it to fit to full width of the application,
     * setting the content and policy for when to show the scroll bars.
     * 
     * @param items - the content, shopping list items.
     */
    public Content (VBox items) {
        setFitToWidth(true);
        setContent(items);
        setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        setHbarPolicy(ScrollBarPolicy.NEVER);
    }
}