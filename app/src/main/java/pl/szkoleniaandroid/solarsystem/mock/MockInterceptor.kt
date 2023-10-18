package pl.szkoleniaandroid.solarsystem.mock

import android.content.Context
import android.util.Log
import com.google.gson.Gson
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import pl.szkoleniaandroid.solarsystem.api.model.ObjectType
import pl.szkoleniaandroid.solarsystem.api.model.SolarObjectDetailsJson
import java.io.IOException
import java.nio.charset.Charset

class MockInterceptor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val specialResponse = handleSpecialHeaders(chain)
        if (specialResponse != null) {
            return specialResponse
        }
        val path = chain.request().url.encodedPath
        return when {
            path.endsWith(".jpg") -> interceptImage(
                chain.request().url.pathSegments.last(), chain
            )

            path == "/planets" -> returnPlanets(chain)
            path == "/others" -> returnOthers(chain)
            path == "/objects_with_moons" -> returnObjectsWithMoons(chain)
            path.startsWith("/moons/") -> returnMoons(
                chain,
                chain.request().url.pathSegments.last()
            )

            path.startsWith("/objects/") -> returnObjectDetails(
                chain,
                chain.request().url.pathSegments.last()
            )

            else -> return404(chain)
        }
    }

    private fun handleSpecialHeaders(chain: Interceptor.Chain): Response? {
        val delay = chain.request().header("Delay")
        if (delay != null) {
            Thread.sleep(delay.toLong())
        }
        val error = chain.request().header("ErrorCode")
        if (error != null) {
            return Response.Builder()
                .code(error.toInt())
                .request(chain.request())
                .message("Internal Server Error")
                .body("".toResponseBody())
                .protocol(Protocol.HTTP_1_0)
                .build()
        }
        val io = chain.request().header("IO")
        if (io != null) {
            throw IOException(io)
        }
        return null
    }

    private fun returnObjectDetails(chain: Interceptor.Chain, objectId: String): Response {
        return return200WithJson(
            chain, SolarObjectDetailsJson(
                id = objectId,
                description = context.loadStringFromAssets("texts/$objectId.txt")
            )
        )
    }

    private fun returnMoons(chain: Interceptor.Chain, orbitId: String): Response {
        return return200WithJson(chain, objects.filter { candidate ->
            candidate.objectType == ObjectType.MOON && candidate.orbitsId == orbitId
        })
    }

    private fun returnObjectsWithMoons(chain: Interceptor.Chain): Response {
        return return200WithJson(chain, objects.filter { candidate ->
            candidate.objectType != ObjectType.MOON && objects.any { it.orbitsId == candidate.id }
        })
    }

    private fun returnOthers(chain: Interceptor.Chain): Response {
        return return200WithJson(chain,
            objects.filter {
                it.objectType == ObjectType.OTHER
            }
        )
    }

    private fun return404(chain: Interceptor.Chain): Response {
        return Response.Builder()
            .code(404)
            .message("Not found")
            .body("".toResponseBody())
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .build()
    }

    private fun returnPlanets(chain: Interceptor.Chain): Response {
        return return200WithJson(chain,
            objects.filter {
                it.objectType == ObjectType.PLANET
            }
        )
    }

    private fun return200WithJson(chain: Interceptor.Chain, body: Any): Response {
        val gson = Gson()
        return Response.Builder()
            .code(200)
            .message("OK")
            .request(chain.request())
            .protocol(Protocol.HTTP_1_0)
            .body(
                ResponseBody.create(
                    "application/json".toMediaType(),
                    gson.toJson(body).toByteArray()
                )
            )
            .build()
    }

    private fun interceptImage(filename: String, chain: Interceptor.Chain): Response {
        return Response.Builder()
            .code(200)
            .protocol(Protocol.HTTP_1_0)
            .request(chain.request())
            .message("OK")
            .body(getJsonDataFromAsset(filename).toResponseBody("image/jpeg".toMediaType()))
            .build()

    }

    private fun getJsonDataFromAsset(fileName: String): ByteArray {
        val path = "images/$fileName"

        return try {
            val stream = context.assets.open(path)
            return ByteArray(stream.available()).apply {
                stream.read(this)
                stream.close()
            }
        } catch (e: IOException) {
            ByteArray(0)
        }
    }
}

fun Context.loadStringFromAssets(fileName: String): String {
    return try {
        val inputStream = assets.open(fileName)
        val size = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()
        String(buffer, Charset.forName("UTF-8"))
    } catch (e: IOException) {
        Log.e("MOCK", "Error loading text", e)
        ""
    }
}
