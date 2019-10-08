// ==UserScript==
// @name         MeMezBotz V2
// @namespace    Free MultiOgar Bots
// @version      2
// @description  MeMezBotz
// @author       BadPlayer
// @match        http://agar.bio/*
// @match        http://cellcraft.io/*
// @match        http://play.agario0.com/*
// @match        http://www.cellcraft.io/*
// @match        http://senpa.io/*
// @run-at       document-end
// @require      https://cdnjs.cloudflare.com/ajax/libs/socket.io/1.7.3/socket.io.min.js
// @require      https://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js
// @require      https://code.jquery.com/ui/1.12.1/jquery-ui.min.js
// @grant        none
// ==/UserScript==
(function() {
    'use strict';
    window.user = {
        x: 0,
        y: 0,
        ip: null,
        origin: null
    };
     document.addEventListener('keydown', function(e) {
        var key = e.keyCode || e.which;
        switch (key) {
            case 69:
                socket.emit('split');
                break;
            case 82:
                socket.emit('eject');
                break;
            case 67:
                socket.emit('spam');
                break;
        }
    });
    WebSocket.prototype.Asend = WebSocket.prototype.send;
    WebSocket.prototype.send = function(a) {
        this.Asend(a);
        var msg = new DataView(a);
        if (msg.byteLength === 21) {
            if (msg.getInt8(0, true) === 16) {
                window.user.x = msg.getFloat64(1, true);
                window.user.y = msg.getFloat64(9, true);
            }
        }
        if (msg.byteLength === 13) {
            if (msg.getUint8(0, true) === 16) {
                window.user.x = msg.getInt32(1, true);
                window.user.y = msg.getInt32(5, true);
            }
        }
        if (msg.byteLength === 5 || msg.byteLength < 4) {
            if (msg.getUint8(0, true) === 16) {
                window.user.x = msg.getInt16(1, true);
                window.user.y = msg.getInt16(3, true);
            }
        }
        if (this.url !== null) {
            window.user.ip = this.url;
            console.log(window.user.ip);
        }
        window.user.origin = location.origin;
    };
    var socket = io.connect('ws://localhost:8080');

    setInterval(function() {
        socket.emit('movement', {
            x: window.user.x,
            y: window.user.y,
        });
    }, 100);
    setInterval(function() {
        console.log("x: " + window.user.x + " y: " + window.user.y);
    }, 100);
    window.start = function() {
        socket.emit('start', {
            ip: window.user.ip !== null ? window.user.ip : 0,
            origin: location.origin
        });
    };
    setTimeout(function() { //<div style='box-shadow: 0px 0px 20px black;z-index:9999999; background-color: #000000; -moz-opacity: 0.4; -khtml-opacity: 0.4; opacity: 0.7; zoom: 1; width: 205px; top: 300px; left: 10px; display: block; position: absolute; text-align: center; font-size: 15px; color: #ffffff; font-family: Ubuntu;border: 2px solid #0c31d4;'> <div style='color:#ffffff; display: inline; -moz-opacity:1; -khtml-opacity: 1; opacity:1;font-size: 22px; filter:alpha(opacity=100); padding: 10px;'><a>Trap Client</a></div> <div style=' color:#ffffff; display: inline; -moz-opacity:1; -khtml-opacity: 1; opacity:1; filter:alpha(opacity=100); padding: 10px;'><br>Minions: <a id='minionCount'>Offline</a> </div><button id='start-bots' style='display: block;border-radius: 5px;border: 2px solid #6495ED;background-color: #BCD2EE;height: 50px;width: 120px;margin: auto;text-align: center;'>StartBots </button><marquee>TrapKillo - Owner</marquee> </div>
        $("#canvas").after("<div  id = 'gui' style='box-shadow: 0px 0px 20px black;z-index:9999999; background-color: #000000; -moz-opacity: 0.4; -khtml-opacity: 0.4; opacity: 0.7; zoom: 1; width: 205px; top: 300px; left: 10px; display: block; position: absolute; text-align: center; font-size: 15px; color: #ffffff; font-family: Ubuntu;border: 2px solid #0c31d4; border-radius: 15px 50px;'> <div style='color:#ffffff; display: inline; -moz-opacity:1; -khtml-opacity: 1; opacity:1;font-size: 22px; filter:alpha(opacity=100); padding: 10px;'><a id='Client_Name'>Agar infinity</a></div> <div style=' color:#ffffff; display: inline; -moz-opacity:1; -khtml-opacity: 1; opacity:1; filter:alpha(opacity=100); padding: 10px;'><br>Minions: <a id='minionCount'>Offline</a> </div><button id='start-bots' style='display: block;border-radius: 5px;border: 2px solid #6495ED;background-color: #BCD2EE;height: 50px;width: 120px;margin: auto;text-align: center;'>StartBots </button></div>");
        document.getElementById('start-bots').onclick = function() {
            window.start();
        };
    }, 2000);
    socket.on('botCount', function(count) {
        $('#minionCount').html(count);
    });
})();

var speed = 40;
var hex = new Array("00", "14", "28", "3C", "50", "64", "78", "8C", "A0", "B4", "C8", "DC", "F0");
var r = 1;
var g = 1;
var b = 1;
var seq = 1;

function changetext() {
    var rainbow = "#" + hex[r] + hex[g] + hex[b];
    document.getElementById("gui").style.borderColor = rainbow;
    document.getElementById("Client_Name").style.color = rainbow;
    document.getElementById("minionCount").style.color = rainbow;
}

function change() {
    if (seq == 6) {
        b--;
        if (b == 0)
            seq = 1;
    }
    if (seq == 5) {
        r++;
        if (r == 12)
            seq = 6;
    }
    if (seq == 4) {
        g--;
        if (g == 0)
            seq = 5;
    }
    if (seq == 3) {
        b++;
        if (b == 12)
            seq = 4;
    }
    if (seq == 2) {
        r--;
        if (r == 0)
            seq = 3;
    }
    if (seq == 1) {
        g++;
        if (g == 12)
            seq = 2;
    }
    changetext()
}
setTimeout(function() {
    setInterval(function() {
        change();
    }, speed);
}, 3000);
setTimeout(function() {
    $(function() {
        $("#gui").draggable();
    });
}, 3000);

/*<div id='particles-js'style='box-shadow: 0px 0px 0px black;z-index:9999999; background-color: #000000; -moz-opacity: 0.4; -khtml-opacity: 0.4; opacity: 0.7; zoom: 1; width: 500px;height: 50%; top: 0px; left: 150px; display: block; position: absolute; text-align: center; font-size: 15px; color: #ffffff; font-family: Ubuntu;'>


</div>
<div style ='z-index:0; background-color: #000000; -moz-opacity: 0.4; -khtml-opacity: 0.4; opacity: 0.7; zoom: 1; width: 150px;height: 50%; top: 0px; left: 0px; display: block; position: absolute; text-align: center; font-size: 15px; color: #ffffff; font-family: Ubuntu;'>
</div>
<script src="https://cdn.jsdelivr.net/particles.js/2.0.0/particles.min.js"></script>*/
