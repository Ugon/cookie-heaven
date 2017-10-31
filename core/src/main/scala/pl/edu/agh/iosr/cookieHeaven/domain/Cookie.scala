package pl.edu.agh.iosr.cookieHeaven.domain

sealed trait Cookie

case object FruitCookie extends Cookie
case object ChocolateCookie extends Cookie
case object SugarFreeCookie extends Cookie
