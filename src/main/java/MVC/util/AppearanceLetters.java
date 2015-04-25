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

        //Это конечно трэш....

        stringBuilder.append("<!DOCTYPE HTML><html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" >");
        stringBuilder.append("<title> - </title>");
        stringBuilder.append("</head><body style=\"padding:0px;margin:0px;\" bgcolor=\"#282f37\">");
        stringBuilder.append("<table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        stringBuilder.append("<tr><td align=\"center\" bgcolor=\"#282f37\">");
        stringBuilder.append("<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\" width=\"600\" style=\"min-width:600px;\">");
        stringBuilder.append("<!--preheader --><tr><td><!-- padding -->");
        stringBuilder.append("<div style=\"height: 12px; line-height:12px; font-size:10px;\">&nbsp;</div>");
        stringBuilder.append("<table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        stringBuilder.append("<tr><td width=\"400\" align=\"center\" valign=\"middle\" style=\"line-height:15px;\">");
        stringBuilder.append("<font face=\"Tahoma, Arial, Helvetica, sans-serif\" size=\"2\" color=\"#ffffff\" style=\"font-size:13px;\">");
        stringBuilder.append("<span style=\"font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 13px; color:#ffffff;line-height:15px;\">");
        stringBuilder.append("Ваш заказ на WebShop");
        stringBuilder.append("</span></font></table>");
        stringBuilder.append("<!-- padding --><div style=\"height: 8px; line-height:8px; font-size:6px;\">&nbsp;</div>");
        stringBuilder.append("</td></tr><!--preheader END--><!--header --><tr><td align=\"center\" bgcolor=\"#ffffff\" ");
        stringBuilder.append("style=\"border-bottom-width:1px;border-bottom-style:solid;border-bottom-color:#282f37;\">");
        stringBuilder.append("<!-- padding --><div style=\"height: 12px; line-height:12px; font-size:10px;\">&nbsp;</div>");
        stringBuilder.append("<!-- padding --><div style=\"height: 12px; line-height:12px; font-size:10px;\">&nbsp;</div>");
        stringBuilder.append("</td></tr><!--header END--><!--content -->");
        stringBuilder.append("<tr><td align=\"center\" bgcolor=\"#ffffff\" style=\"border-top-width:1px;border-top-style:solid;border-top-color:#ffffff;\">");
        stringBuilder.append("<!--sep --><table width=\"600\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        stringBuilder.append("<tr><td height=\"14\" bgcolor=\"#f27071\" style=\"height:14px;line-height:14px;font-size:10px;\">&nbsp;</td></tr></table><!--sep END-->");
        stringBuilder.append("<table width=\"490\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\"><tr><td align=\"left\">");
        stringBuilder.append("<!-- padding --><div style=\"height: 45px; line-height:45px; font-size:40px;\">&nbsp;</div><!--hello -->");
        stringBuilder.append("<div style=\"line-height:24px;\"><font face=\"Tahoma, Arial, Helvetica, sans-serif\" size=\"3\" ");
        stringBuilder.append("color=\"#282f37\" style=\"font-size:16px;\">");
        stringBuilder.append("<span style=\"font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 16px; color:#282f37; \">");
        stringBuilder.append("<strong>Добрый день, " + order.getName() + "!</strong><br /><br /></span></font>");
        stringBuilder.append("<p align=\"center\" style=\"font-size:21px; color: green;\">Ожидайте товар в течение 10 лет, он обязательно к вам придет");
        stringBuilder.append("<p style=\"font-size:15px\">Адресс доставки: ");
        stringBuilder.append("<span style=\"font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 16px; color:rgb(172, 125, 210);");
        stringBuilder.append("text-decoration:underline \">" + order.getAddress() + "</span>.</p>");
        stringBuilder.append("</div><!--hello END--><!-- padding --><div ");
        stringBuilder.append("style=\"height: 25px; line-height:25px; font-size:23px;\">&nbsp;</div>");
        stringBuilder.append("<table width=\"490\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        stringBuilder.append("<tr><td width=\"210\" align=\"left\" valign=\"top\">");
        stringBuilder.append("<width=\"180\" height=\"180\" alt=\"\" border=\"0\" style=\"display: block;\" /></td>");
        stringBuilder.append("<td align=\"left\" valign=\"middle\" style=\"line-height:24px;\">");
        stringBuilder.append("<font face=\"Tahoma, Arial, Helvetica, sans-serif\" size=\"3\" color=\"#282f37\" style=\"font-size:16px;\">");
        stringBuilder.append("<span style=\"font-family: Tahoma, Arial, Helvetica, sans-serif; font-size: 16px; color:#282f37;line-height:24px;\">");
        stringBuilder.append("</span></font><!-- padding --><div style=\"height: 26px; line-height:26px; font-size:23px;\">&nbsp;</div>");
        stringBuilder.append("<!--Button Begin--><table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        stringBuilder.append("<tr><td align=\"center\" width=\"250\" height=\"39\" bgcolor=\"#f27071\" style=\"line-height: 39px;\">");
        stringBuilder.append("<a href=\"http://localhost:8080/#/order/" + order.getUuid() + "\"  ");
        stringBuilder.append("style=\"color: #ffffff; font-family: Arial, Helvetica, sans-serif; font-size: 16px;text-decoration:none;\" >");
        stringBuilder.append("<font face=\"Arial, Helvetica, sans-serif\" size=\"3\" color=\"#ffffff\">");
        stringBuilder.append("</font>Перейти к заказу</a></td></tr></table>");
        stringBuilder.append("<!--Button End--></td></tr></table>");
        stringBuilder.append("<!-- padding --><div style=\"height: 50px; line-height:50px; font-size:45px;\">&nbsp;</div>");
        stringBuilder.append("</td></tr></table><!--sep --><table width=\"100%\" border=\"0\" cellspacing=\"0\" cellpadding=\"0\">");
        stringBuilder.append("<tr><td height=\"10\" style=\"line-height:10px;font-size:8px;border-top-width:2px;");
        stringBuilder.append("border-top-style:solid;border-top-color:#282f37;\">&nbsp;</td></tr>");
        stringBuilder.append("</table><!--sep END--><!-- padding --><div style=\"height: 30px; line-height:30px; font-size:28px;\">&nbsp;</div>");
        stringBuilder.append("</td></tr><!--content END--><!--footer --><tr><td align=\"center\" bgcolor=\"#282f37\">");
        stringBuilder.append("<!-- padding --><div style=\"height: 30px; line-height:30px; font-size:28px;\">&nbsp;</div>");
        stringBuilder.append("<!-- padding --><div style=\"height: 30px; line-height:30px; font-size:28px;\">&nbsp;</div>");
        stringBuilder.append("</td></tr><!--footer END--></table></body></html>");

        return stringBuilder.toString();
    }
}
