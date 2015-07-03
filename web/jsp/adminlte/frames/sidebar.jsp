<%@ page contentType="text/html;charset=UTF-8" language="java" %><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    <aside class="main-sidebar">
        <section class="sidebar">
            <div class="user-panel">
                <div class="pull-left image">
                    <img src="http://re.vo.org.ua/lte/dist/img/user2-160x160.jpg" class="img-circle" alt="User Image" />
                </div>
                <div class="pull-left info">
                    <p>${PC.getUser().getProfile().getFullName()}</p>
                    <a href="#"><i class="fa fa-circle text-success"></i> Online</a>
                </div>
            </div>
            <ul class="sidebar-menu">
                <li class="treeview"><a href="/"><i class="fa fa-home"></i> <span>Вернуться на сайт</span></a></li><c:if test="${PC.hasAny(\"CORE\", \"CONFIG\", \"SECURITY\", \"NODE\", \"LANGUAGE\", \"USER\", \"LOCATION\", \"ACTION\", \"PAGE\", \"MODULE\")}">
                <li class="header">Основные настройки</li>
                <c:if test="${PC.hasAnyAccess(\"SETTINGS\")}"><li class="treeview"><a href="#"><i class="fa fa-wrench"></i> <span>Настройки</span></a></li></c:if>
                <c:if test="${PC.hasAnyAccess(\"SECURITY\")}"><li class="treeview">
                    <a href="#"><i class="fa fa-lock"></i> <span>Безопасность</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-list"></i> Список пользователей</a></li>
                        <li><a href="#"><i class="fa fa-lock"></i> Группы доступа</a></li>
                    </ul>
                </li></c:if>

                <c:if test="${PC.hasAnyAccess(\"LANGUAGE\")}"><li class="treeview<c:if test="${PC.getModule() == \"languages\"}"> active</c:if>">
                    <a href="/${HOME}/languages"><i class="fa fa-comment"></i> <span>Мультиязычность</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu<c:if test="${PC.getModule() == \"languages\"}"> menu-open</c:if>">
                        <c:if test="${PC.hasAnyAccess(\"LANGUAGE\")}"><li<c:if test="${PC.getModule() == \"languages\" && PC.getAction() == \"list\"}"> class="active"</c:if>><a href="/${HOME}/languages"><i class="fa fa-list"></i> Список языков</a></li></c:if>
                        <c:if test="${PC.hasAccess(\"LANGUAGE\", \"MODIFY_WORDS\")}"><li><a href="#"><i class="fa fa-key"></i> Словарные ключи</a></li></c:if>
                    </ul>
                </li></c:if>
                <c:if test="${PC.hasAnyAccess(\"USER\")}"><li class="treeview">
                    <a href="#"><i class="fa fa-users"></i> <span>Пользователи</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-list"></i> Список пользователей</a></li>
                        <li><a href="#"><i class="fa fa-unlock-alt"></i> Группы доступа</a></li>
                    </ul>
                </li></c:if>
                <c:if test="${PC.hasAny(\"PAGE\", \"MODULE\")}"><li class="treeview">
                    <a href="#"><i class="fa fa-th"></i> <span>Клиентская часть</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-th-large"></i> Страницы</a></li>
                        <li><a href="#"><i class="fa fa-list-ol"></i> Меню</a></li>
                    </ul>
                </li></c:if>
                </c:if>
                <c:if test="${PC.hasAny(\"POST\", \"VIDEO\", \"IMAGE\")}"><li class="header">Медиа контент</li>
                <li class="treeview">
                    <a href="#"><i class="fa fa-youtube-play"></i> <span>Видео</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-list"></i> Список видео</a></li>
                        <li><a href="#"><i class="fa fa-indent"></i> Видео категории</a></li>
                        <li><a href="#"><i class="fa fa-youtube-square"></i> Видео источники</a></li>
                    </ul>
                </li>
                <li class="treeview">
                    <a href="#"><i class="fa fa-file-text"></i> <span>Посты</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-list"></i> Список постов</a></li>
                        <li><a href="#"><i class="fa fa-indent"></i> Категории постов</a></li>
                    </ul>
                </li>
                <li class="treeview">
                    <a href="#"><i class="fa fa-picture-o"></i> <span>Изображения</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-list"></i> Список изображений</a></li>
                        <li>
                            <a href="#"><i class="fa fa-arrows"></i> Размеры изображений <i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li><a href="#"><i class="fa fa-list"></i> Типы размеров</a></li>
                                <li><a href="#"><i class="fa fa-plus-circle"></i> Добавить тип</a></li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="fa fa-instagram"></i> Хранилища изображений</a></li>
                    </ul>
                </li></c:if>
                <li class="header">Социальная сеть</li>
                <li class="treeview">
                    <a href="#"><i class="fa fa-users"></i> <span>Пользователи</span> <i class="fa fa-angle-left pull-right"></i></a>
                    <ul class="treeview-menu">
                        <li><a href="#"><i class="fa fa-list"></i> Список пользователей</a></li>
                        <li>
                            <a href="#"><i class="fa fa-arrows"></i> Размеры изображений <i class="fa fa-angle-left pull-right"></i></a>
                            <ul class="treeview-menu">
                                <li><a href="#"><i class="fa fa-list"></i> Типы размеров</a></li>
                                <li><a href="#"><i class="fa fa-plus-circle"></i> Добавить тип</a></li>
                            </ul>
                        </li>
                        <li><a href="#"><i class="fa fa-instagram"></i> Хранилища изображений</a></li>
                    </ul>
                </li>
            </ul>
        </section>
    </aside>