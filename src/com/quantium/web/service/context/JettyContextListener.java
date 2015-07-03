package com.quantium.web.service.context;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.quantium.web.controller.WebSocketController;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.DefaultHandler;

/**
 * Created by FREEMAN on 02.12.14.
 */
public class JettyContextListener implements ServletContextListener {

    /**
     * Хранилище сервера Jetty
     */
    private Server server = null;

    /**
     * Метод вызывается когда контейнер сервлетов запускается
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {

        try {
            // Создание сервера Jetty на 8081 порту
            this.server = new Server(8081);

            // Регистрируем ChatWebSocketHandler в сервере Jetty
            WebSocketController chatWebSocketHandler = new WebSocketController();

            // Это вариант хэндлера для WebSocketHandlerContainer
            chatWebSocketHandler.setHandler(new DefaultHandler());

            // Вставляем наш хэндлер слушаться jetty
            server.setHandler(chatWebSocketHandler);

            // Запускаем Jetty
            server.start();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод вызывается когда контейнер сервлетов останавливается
     * @param event
     */
    @Override
    public void contextDestroyed(ServletContextEvent event) {

        // Если сервер jetty когда-нибудь запустился
        if (server != null) {
            try {
                // останавливаем Jetty
                server.stop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
