package jp.satoshun.chreco.libs;


import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;

public class MyHttp {
    public static String post(String url, Map<String, String> params) {
        HttpResponse res = getHttpResponsePost(url, params);
        return getResponseData(res);
    }

    private static HttpResponse getHttpResponsePost(String url,
            Map<String, String> params) {
        HttpPost req = new HttpPost(url);
        req.setParams(getHttpParams(params));
        return getHttpResponse(req);
    }

    private static HttpParams getHttpParams(Map<String, String> params) {
        HttpParams postParams = new BasicHttpParams();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            postParams.setParameter(entry.getKey(), entry.getValue());
        }

        return postParams;
    }

    private static HttpResponse getHttpResponse(HttpRequestBase hr) {
        try {
            HttpClient client = new DefaultHttpClient();
            return client.execute(hr);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static String getResponseData(HttpResponse res) {
        if (res == null) {
            return null;
        }

        int status = res.getStatusLine().getStatusCode();
        if (HttpStatus.SC_OK == status) {
            ByteArrayOutputStream os = new ByteArrayOutputStream();

            try {
                res.getEntity().writeTo(os);
                return os.toString();
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        return null;
    }
}
