package org.mizuro.aviatickets.controllers;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ErrorControllerImpl implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        // Получить информацию об ошибке из запроса
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        // Обработать различные типы ошибок и вернуть соответствующую страницу ошибки
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/error-404"; // Пользовательская страница для 404 ошибки
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/error-500"; // Пользовательская страница для 500 ошибки
            }
        }
        return "error/error"; // Пользовательская страница для других ошибок
    }
}