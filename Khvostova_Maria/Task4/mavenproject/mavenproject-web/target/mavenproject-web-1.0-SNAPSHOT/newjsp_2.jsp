

<%@page import="java.util.Map"%>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<%@page import="com.mycompany.CharacterParser" %> 
<%@page import="com.mycompany.MySessionBean" %>

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
          String prefix=request.getParameter("prefix");
          String ejbRefName= request.getParameter("name");
          toParse=request.getParameter("String");
          String jndiUrl="java:global/mavenproject-ear-1.0-SNAPSHOT/mavenproject-ejb-1.0-SNAPSHOT/MySessionBean";
          //String jndiUrl = prefix + ejbRefName;    //The JNDI URL should be "java:comp/env/SessionFacade2RefName"
          javax.naming.Context ctx = new javax.naming.InitialContext();
          MySessionBean ch=(MySessionBean)ctx.lookup( jndiUrl );
          Map<Character,Integer> resultMap = ch.parseString(toParse);
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
          <form action="newjsp_2.jsp" method="POST" >
            <textarea name="String" ></textarea>
            <input type="submit"/> 
        </form>
    </body>
</html>
