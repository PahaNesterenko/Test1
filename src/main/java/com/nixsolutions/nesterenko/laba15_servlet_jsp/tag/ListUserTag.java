package com.nixsolutions.nesterenko.laba15_servlet_jsp.tag;

import java.io.IOException;
import java.sql.Date;
import java.util.Calendar;
import java.util.List;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.nixsolutions.nesterenko.laba16_hibernate.entity.User;

public class ListUserTag extends SimpleTagSupport {

    private List<User> userList = null;

    @SuppressWarnings("unchecked")
    public void setUserList(Object userList) {
        this.userList = (List<User>) userList;
    }

    public void doTag() throws JspException, IOException {
        JspWriter out = getJspContext().getOut();

        try {
            out.print("<table style=\"margin: 0 auto; width: 100%\" border= \"1px align=\"left\"> "
                    + "<tr align=\"left\"> "
                    + "<th>Login</th>"
                    + "<th>First name</th>"
                    + "<th>Last name</th> "
                    + "<th>Age</th> "
                    + "<th>Role</th> "
                    + "<th>Actions</th> "
                    + "</tr>");

            if (userList != null) {
                for (User user : userList) {
                    out.print("<tr>");

                    out.print("<td>");
                    out.print(user.getLogin());
                    out.print("</td>");

                    out.print("<td>");
                    out.print(user.getFirstName());
                    out.print("</td>");

                    out.print("<td>");
                    out.print(user.getLastName());
                    out.print("</td>");

                    out.print("<td>");
                    out.print(countAge(user.getBirthday()));
                    out.print("</td>");

                    out.print("<td>");
                    out.print(user.getRole().getName());
                    out.print("</td>");

                    out.print("<td>");
                    out.print("<a href=\"preEditUser?userId=" + user.getId() + "\">Edit</a>");
                    out.print(" ");
                    out.print("<a onClick=\"return window.confirm('Are you sure?')\" href=\"deleteUser?userId=" + user.getId() + "\">Delete</a>");
                    out.print("</tr>");
                }
            }
            out.print("</table>");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private int countAge(Date bDate) {
        Calendar user = Calendar.getInstance();
        user.setTime(bDate);
        Calendar today = Calendar.getInstance();
        int age = today.get(Calendar.YEAR) - user.get(Calendar.YEAR);
        if (today.get(Calendar.MONTH) < user.get(Calendar.MONTH)) {
            age--;
        } else if (today.get(Calendar.MONTH) == user.get(Calendar.MONTH)
                && today.get(Calendar.DAY_OF_MONTH) < user
                        .get(Calendar.DAY_OF_MONTH)) {
            age--;
        }
        return age;
    }
}
