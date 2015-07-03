<%@ page contentType="text/html;charset=UTF-8" language="java" %>
        <div id="footerTop" class="bgBlue active">
            <div class="content">
                <ul class="columns columnsFlex">
                    <li class="fC2">
                        <p class="normal fs24">${PC.getWord("footer.contacts.kiev.city")}:</p>
                        <p class="normal">${PC.getWord("footer.contacts.kiev.address")}</p>
                        <p class="normal">${PC.getWord("footer.contacts.kiev.time")}</p>
                        <p class="normal">${PC.getWord("footer.contacts.kiev.contacts")}</p>
                        <div id="ymaps-map-id_134245032582377589794" style="width: 495px; height: 220px;"></div>
                        <%--<script type="text/javascript">function fid_134245032582377589794(ymaps) {var map = new ymaps.Map("ymaps-map-id_134245032582377589794", {center: [30.567507457670263, 50.397915147996045], zoom: 16, type: "yandex#map"});map.controls.add("zoomControl").add("mapTools").add(new ymaps.control.TypeSelector(["yandex#map", "yandex#satellite", "yandex#hybrid", "yandex#publicMap"]));map.geoObjects.add(new ymaps.Placemark([30.567507457670263, 50.397915147996045], {balloonContent: '${PC.getWord("logo.top")} ${PC.getWord("logo.bottom")}<br/>${PC.getWord("footer.contacts.kiev.address")}'}, {preset: "twirl#lightblueDotIcon"}));};</script>--%>
                        <%--<script type="text/javascript" src="http://api-maps.yandex.ru/2.0/?coordorder=longlat&load=package.full&wizard=constructor&lang=ru-RU&onload=fid_134245032582377589794"></script>--%>
                    </li>
                    <li class="fC2">
                        <p class="normal fs24">${PC.getWord("footer.contacts.dnepr.city")}:</p>
                        <p class="normal">${PC.getWord("footer.contacts.dnepr.address")}</p>
                        <p class="normal">${PC.getWord("footer.contacts.dnepr.time")}</p>
                        <p class="normal">${PC.getWord("footer.contacts.dnepr.contacts")}</p>
                        <div id="ymaps-map-id_134088483229966023102" style="width: 495px; height: 220px;"></div>
                        <%--<script type="text/javascript">function fid_134088483229966023102(ymaps) {var map = new ymaps.Map("ymaps-map-id_134088483229966023102", {center: [35.004589855215215, 48.45419590723767], zoom: 16, type: "yandex#map"});map.controls.add("zoomControl").add("mapTools").add(new ymaps.control.TypeSelector(["yandex#map", "yandex#satellite", "yandex#hybrid", "yandex#publicMap"]));map.geoObjects.add(new ymaps.Placemark([35.004589855215215, 48.45419590723767], {balloonContent: '${PC.getWord("logo.top")} ${PC.getWord("logo.bottom")}<br/>${PC.getWord("footer.contacts.dnepr.address")}'}, {preset: "twirl#lightblueDotIcon"}));};</script>--%>
                        <%--<script type="text/javascript" src="http://api-maps.yandex.ru/2.0/?coordorder=longlat&load=package.full&wizard=constructor&lang=ru-RU&onload=fid_134088483229966023102"></script>--%>
                    </li>
                </ul>
            </div>
        </div>