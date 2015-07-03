<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>




<div class="m10">
    <form method="post" action="/${HOME}/${PC.getModule()}/${PC.getAction()}">
        <input type="hidden" name="languageId" value="${language.getLanguageId()}"/>
        <table class="mb10">
            <thead>
            <tr>
                <td class="fwB oH">#ID</td>
                <td class="oH">Ключ слова</td>
                <td class="oH">Значение слова</td>
                <td class="oH">Описание</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${keys}" var="key"><tr><c:set var="value" value="${language.getValue(key.getKey())}"/><c:set var="_key" value="${key.getKey()}"/>
                <td class="fwB">#${value.getValueId()}</td>
                <td>${_key}</td>
                <td><input type="text" name="${_key}" class="formInput bgWhite" value="${value.getValue()}" placeholder="${_key}" ui-style/></td>
                <td>${key.getInfo()}</td>
            </tr></c:forEach>
            </tbody>
            <tfoot>
            <tr>
                <td class="fwB oH">#ID</td>
                <td class="oH">Ключ слова</td>
                <td class="oH">Значение слова</td>
                <td class="oH">Описание</td>
            </tr>
            </tfoot>
        </table>
        <input type="submit" class="button full taC bgGreen hover" value="Сохранить"/>
    </form>
</div>