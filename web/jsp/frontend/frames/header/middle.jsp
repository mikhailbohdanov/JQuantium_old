<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %><%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ page import="com.quantium.web.controller.manager.ManagerGenerator" %><%@ page import="com.quantium.web.util.UserHelper" %>
        <div id="headerMiddle" class="bgBlue">
            <div class="content">
                <ul class="columns columnsFlex">
                    <li class="fC">
                        <a href="/" id="logotype" class="bgParent hover">
                            <div class="image logotype"></div>
                            <div class="top">${PC.getWord("logo.top")}</div>
                            <div class="bottom">${PC.getWord("logo.bottom")}</div>
                        </a>
                    </li>
                    <li class="fC">
                        <sec:authorize access="hasRole('ROLE_ADMIN')"><a href="/<%=ManagerGenerator.MAIN_PAGE %>" class="button menuButton bgParent hover pl10 pr10">${PC.getWord("header.manager")}</a></sec:authorize>
                        <a class="button menuButton bgParent hover" ui-menu="profile">
                            <span class="flL icon user white"><span></span></span>
                            <span class="flR icon arrow small white bottom"><span></span></span>
                            <% if (UserHelper.isAuthorized()) { %>${PC.getUser().getProfile().getFullName()}<% } else { %>${PC.getWord("header.yourProfile")}<% } %>
                        </a>
                        <a class="button menuButton bgParent hover" ui-menu="pages">
                            <span class="flL icon menu white"><span></span></span>
                            <span class="flR icon arrow small white bottom"><span></span></span>
                            ${PC.getWord("header.allSections")}
                        </a>
                    </li>
                </ul>
            </div>
            <div id="menuWrapper" class="bgWhite hide">
                <div id="pages" class="content hide">
                    <div class="header ttU">
                        ${PC.getWord("header.allSections")}
                        <a href="#" class="button icon cross flR"><span></span></a>
                    </div>
                    <div class="content">
                        <ul class="columns columnsFlex">
                            <li class="fC4">
                                <div class="mb10">
                                    <a href="#" class="link cBlue hBlue fs20">Main page</a>
                                </div>
                                <div class="mb10">
                                    <a href="#" class="link cSilver hSilver fs14">Other page</a>
                                </div>
                                <div class="mb10">
                                    <a href="#" class="link cSilver hSilver fs14">Other page</a>
                                </div>
                                <div class="mb10">
                                    <a href="#" class="link cSilver hSilver fs14">Other page</a>
                                </div>
                                <div class="mb10">
                                    <a href="#" class="link cSilver hSilver fs14">Other page</a>
                                </div>
                                <div class="mb10">
                                    <a href="#" class="link cSilver hSilver fs14">Other page</a>
                                </div>
                            </li>
                            <li class="fC4">
                                <div class="mb10">
                                    <a href="#" class="link cBlue hBlue fs20">Main page</a>
                                </div>
                                <div class="mb10">
                                    <a href="#" class="link cBlue hBlue fs20">Main page</a>
                                </div>
                            </li>
                            <li class="fC4">
                                3
                            </li>
                            <li class="fC4">
                                4
                            </li>
                        </ul>
                    </div>
                </div>
                <div id="profile" class="content hide"><sec:authorize access="isAuthenticated()">
                    <div class="header ttU">
                        ${PC.getWord("header.yourProfile")}
                        <a href="#" class="button icon cross flR"><span></span></a>
                    </div>
                    <div class="content">
                        <div class="oH">
                            <div class="flL imageLoad profilePhoto w300">
                                <img src="/_images/200x200.jpg" alt="" class="br"/>
                                <div class="uploadPhoto">
                                    <div><a href="#" class="link cWhite hWhite">Загрузить фото</a></div>
                                    <div><a href="#" class="link cRed hRed">Удалить фото</a></div>
                                </div>
                            </div>
                            <div class="flL oH w700">
                                <div class="oH ml10 mb10">
                                    <a href="/${PC.getUser().getProfile().getUrl()}" class="flL link cSilver hSilver fs24">${PC.getUser().getProfile().getFullName()}</a>
                                    <div class="iBlock ml10 lh30">
                                        (<a href="#" class="link cSilver hSilver">Ред.</a>)
                                    </div>
                                    <a href="/security/logout" class="flR button bgBlackT hover pl10 pr10">Выйти</a>
                                </div>
                                <div class="flL ml10 w200">
                                    1231
                                </div>
                                <div class="flL ml10 w260">
                                    weq
                                </div>
                                <div class="flR ml10 w200 mb10">
                                    <a href="#" class="button full mb10 bgBlue hover"><span class="ml10">Мои друзья</span> <b>(+3)</b></a>
                                    <a href="#" class="button full mb10 bgBlue hover"><span class="ml10">Мои сообщения</span> <b>(+3)</b></a>
                                    <a href="#" class="button full mb10 bgGreen hover"><span class="ml10">Уроки</span> <b>(+3)</b></a>

                                </div>
                            </div>
                        </div>
                    </div></sec:authorize><sec:authorize access="isAnonymous()">
                    <div class="header ttU">
                            ${PC.getWord("profile.signin")}
                        <a href="#" class="button icon cross flR"><span></span></a>
                    </div>
                    <div class="content">
                        <div class="columns columnsFlex">
                            <div class="fC3">
                                <form action="/security/login" method="post" ui-style>
                                    <input type="text" name="login" class="formInput mb10 bgWhite" placeholder="${PC.getWord("profile.signin.login")}" ui-style/>
                                    <input type="password" name="password" class="formInput mb10 bgWhite" placeholder="${PC.getWord("profile.signin.password")}" ui-style/>
                                    <div class="oH">
                                        <label class="flL formCheckbox">
                                            <input type="checkbox" name="rememberMe"/>
                                                ${PC.getWord("profile.signin.rememberMe")}
                                        </label>
                                        <input type="submit" value="${PC.getWord("profile.signin.goButton")}" class="flR button bgBlue hover pl20 pr20"/>
                                    </div>
                                </form>
                            </div>
                            <div class="fC3">
                                <div class="normal lh30">
                                        ${PC.getWord("profile.prefixSignup")}, <a href="/signup" class="link cBlue hBlue" onclick="NODES.menuAll.addClass('hide');NODES.menuList.signup.removeClass('hide');return false;">${PC.getWord("profile.signupNow")}</a>
                                </div>
                                <div class="normal lh30">
                                        ${PC.getWord("profile.prefixRestorePassword")} <a href="/restore" class="link cBlue hBlue" onclick="NODES.menuAll.addClass('hide');NODES.menuList.restore.removeClass('hide');return false;">${PC.getWord("profile.restorePasswordNow")}</a>
                                </div>

                            </div>
                            <div class="fC3">

                            </div>
                        </div>
                    </div>
                    </sec:authorize>
                </div>
                <sec:authorize access="isAnonymous()">
                <div id="signup" class="content hide">
                    <div class="header ttU">
                        ${PC.getWord("profile.signup")}
                        <a href="#" class="button icon cross flR"><span></span></a>
                    </div>
                    <div class="content">
                        <div class="columns columnsFlex">
                            <div class="fC3">
                                <form action="#">
                                    <div class="normal">
                                        ${PC.getWord("profile.prefixSignin")}, <a href="/signin" class="link cBlue hBlue" onclick="NODES.menuAll.addClass('hide');NODES.menuList.profile.removeClass('hide');return false;">${PC.getWord("profile.signinNow")}</a>
                                    </div>
                                    <input type="text" name="firstName" class="formInput mb10 bgWhite" placeholder="${PC.getWord("profile.signup.firstName")}" ui-style/>
                                    <input type="text" name="lastName" class="formInput mb10 bgWhite" placeholder="${PC.getWord("profile.signup.lastName")}" ui-style/>
                                    <textarea name="about" class="formTextarea mb10 bgWhite aOff" placeholder="${PC.getWord("profile.signup.about")}" ui-style conf-min="2" conf-max="5"></textarea>
                                </form>
                            </div>
                            <div class="fC3">

                            </div>
                            <div class="fC3">

                            </div>
                        </div>
                    </div>
                </div>
                <div id="restore" class="content hide">
                    <div class="header ttU">
                        Восстановление доступа
                        <a href="#" class="button icon cross flR"><span></span></a>
                    </div>
                    <div class="content">

                    </div>
                </div></sec:authorize>
            </div>
        </div>