package controllers

import play.api.Configuration
import javax.inject._
import play.api.mvc.{Action, _}
import play.api.libs.ws._
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration.DurationInt

@Singleton
class ProxyController @Inject()(val controllerComponents: ControllerComponents, val ws: WSClient, val config: Configuration)(implicit ec: ExecutionContext) extends BaseController {

  def proxy(path: String, qNames: List[String], qVals: List[String]): Action[AnyContent] = Action.async {
    implicit request: Request[AnyContent] =>
      val API_KEY = config.get[String]("api_key")
      val baseUrl = config.get[String]("base_url")

      val requestUrl = makeUrl(baseUrl, path, qNames, qVals)
      val wsRequest: WSRequest = ws.url(requestUrl)
      val complexRequest: WSRequest =
        wsRequest.addHttpHeaders("Ocp-Apim-Subscription-Key" -> API_KEY)
          .withRequestTimeout(100000.millis)
      val futureResponse: Future[WSResponse] = complexRequest.get

      for {
        response <- futureResponse
      } yield {
        Ok(response.json)
      }
  }

  private def makeUrl(baseUrl: String, path: String, qNames: List[String], qVals: List[String]): String = {
    var parameterStr = baseUrl + path + "?"
    for (i <- qNames.indices) {
      parameterStr += qNames(i) + "=" + qVals(i) + "&"
    }
    parameterStr
  }

}
