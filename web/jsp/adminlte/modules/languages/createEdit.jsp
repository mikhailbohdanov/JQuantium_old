<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <section class="content">
            <div class="row">
                <div class="col-md-12">
                    <div class="box box-primary">
                        <div class="box-header with-border">
                            <h3 class="box-title">Редактирование языка</h3>
                        </div>

                        <form method="post" action="/${HOME}/languages/save">
                            <c:if test="${language.getLanguageId() > 0}"><input name="languageId" type="hidden" value="${language.getLanguageId()}"/></c:if>
                            <div class="box-body">
                                <div class="row">
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="name">Название языка</label>
                                            <input name="name" type="text" class="form-control" id="name" placeholder="Название" value="${language.getName()}"/>
                                        </div>
                                    </div>
                                    <div class="col-md-12">
                                        <div class="form-group">
                                            <label for="code">Код языка</label>
                                            <input name="code" type="text" class="form-control" id="code" placeholder="Код" value="${language.getCode()}"/>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="box-footer">
                                <input type="submit" class="btn btn-success" value="Сохранить"/>
                                <a href="/${HOME}/languages" class="btn btn-default">Отменить</a>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </section>