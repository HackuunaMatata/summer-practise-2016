<%-- 
    Document   : newjsp
    Created on : 09.07.2016, 17:13:46
    Author     : Елена
--%>

<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="com.mycompany.CharacterParser" %> 

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Hello World!</h1>
        <%! String toParse;%>
        <%
            toParse=request.getParameter("String");
          CharacterParser ch=new CharacterParser();
          Map<Character,Integer> resultMap = ch.processString(toParse);
          %>
          <% if(toParse!=null && toParse.length()>0){  %>
          <table border="1">
              <tr>
                  <th>
                      Буква
                  </th>
                  <th>
                      Кол-во
                  </th>
              </tr>
          
          <%
          for(Map.Entry <Character,Integer> entry: resultMap.entrySet())
          {
              %>
              <tr>
                  <td>
                      <%= entry.getKey() %>             
                  </td>
                  <td>
                      <%= entry.getValue()%>
                  </td>
              </tr>
              <%
          }
        %>
          </table>
          <%}%>
          <form action="newjsp.jsp" method="POST" >
            <textarea name="String" ></textarea>
            <input type="submit"/> 
        </form>
    </body>
</html>
