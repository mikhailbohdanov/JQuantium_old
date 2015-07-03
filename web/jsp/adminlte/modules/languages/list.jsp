<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Список языков</h3>
                            <div class="box-tools pull-right">
                                <c:if test="${PC.hasAccess(\"LANGUAGE\", \"ADD\")}"><a href="/${HOME}/languages/create" class="btn btn-box-tool">
                                    <i class="fa fa-plus text-green"></i>
                                </a></c:if>
                            </div>
                        </div>

                        <div class="box-body">
                            <table class="table table-bordered table-hover">
                                <tr>
                                    <th>#ID</th>
                                    <th>Язык</th>
                                    <th>Код языка</th>
                                    <th>Статус</th>
                                    <th class="text-right">Операции</th>
                                </tr>
                                <c:forEach items="${languages}" var="language">
                                <tr>
                                    <td>#${language.getLanguageId()}</td>
                                    <td>${language.getName()}</td>
                                    <td>${language.getCode()}</td>
                                    <td>
                                        <c:choose><c:when test="${language.isEnabled()}"><button class="btn btn-xs btn-success">Включен</button></c:when><c:otherwise><button class="btn btn-xs btn-default">Выключен</button></c:otherwise></c:choose>
                                    </td>
                                    <td class="text-right">
                                        <c:if test="${PC.hasAccess(\"LANGUAGE\", \"MODIFY\")}"><a href="/${HOME}/languages/edit/${language.getLanguageId()}" class="btn btn-xs btn-info" title="Изменить">
                                            <i class="fa fa-pencil"></i>
                                        </a></c:if>
                                        <c:if test="${PC.hasAccess(\"LANGUAGE\", \"MODIFY_WORDS\")}"><a href="/${HOME}/languages/words/${language.getLanguageId()}" class="btn btn-xs btn-primary" title="Редактировать слова">
                                            <i class="fa fa-book"></i>
                                        </a></c:if>
                                        <c:if test="${PC.hasAccess(\"LANGUAGE\", \"REMOVE\")}"><a href="/${HOME}/languages/delete/${language.getLanguageId()}" class="btn btn-xs btn-danger" title="Удалить">
                                            <i class="fa fa-times"></i>
                                        </a></c:if>
                                    </td>
                                </tr></c:forEach>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
        </section>