package MVC.util;

import MVC.persistence.entities.Order;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;


/**
 * Created by en on 22.04.2015.
 */
@Component
public class AppearanceLetters {
    private final Logger log = Logger.getLogger(AppearanceLetters.class);

    public String sendMailToUser(final Order order) {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">");
        stringBuilder.append("<html xmlns=\"http://www.w3.org/1999/xhtml>");
        stringBuilder.append("<head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />");
        stringBuilder.append(" <title>Заказ из Магазина WebShop</title><style type=\"text/css\">body {margin: 0; padding: 0; min-width: 100%!important;}\n" +
                "        .content {width: 100%; max-width: 1000px;}</style>");
        stringBuilder.append("</head><body yahoo bgcolor=\"#f6f8f1\">");
        stringBuilder.append("<table width=\"100%\" bgcolor=\"#f6f8f1\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\">");
        stringBuilder.append("<tr><td><table class=\"content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">");
        stringBuilder.append(" <tr><td>");
        stringBuilder.append("<br>");
        stringBuilder.append("<br>");
        stringBuilder.append("<br>");
        stringBuilder.append("<h2><a href=\"");
        stringBuilder.append("http://localhost:8080/#/order/");
        stringBuilder.append(order.getUuid() + "\">Ссылка на ваш заказ</a></h2>");
        stringBuilder.append("</td></tr></table></td></tr></table></body></html>");

        return stringBuilder.toString();
    }
}
