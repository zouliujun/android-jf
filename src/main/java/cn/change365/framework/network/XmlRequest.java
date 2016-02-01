package cn.change365.framework.network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import cn.change365.framework.utils.Charsets;
import cn.change365.framework.utils.StringUtils;

/**
 * Created by Jack on 2015/6/18.
 */
public class XmlRequest extends Request<List> {

    private final Response.Listener<List> mListener;
    private final Map<String, String> mHeaders;
    private final BaseXmlParser mParser;
    private Charset mCharset = Charsets.UTF_8;

    public XmlRequest(int method, String url, BaseXmlParser parser, Map<String, String> headers,
                      Response.Listener<List> listener, Response.ErrorListener errorListener, Charset charset) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mHeaders = headers;
        this.mParser = parser;
        this.mCharset = charset;
    }

    public XmlRequest(int method, String url, BaseXmlParser parser, Map<String, String> headers,
                      Response.Listener<List> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        this.mListener = listener;
        this.mHeaders = headers;
        this.mParser = parser;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mHeaders != null ? mHeaders : super.getHeaders();
    }

    protected Response<List> parseNetworkResponse(NetworkResponse response) {
        try {
            String data = new String(response.data, mCharset);
            InputStream is = StringUtils.getInputStreamFromString(data);
            return Response.success(
                    mParser.parse(is),
                    HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(List response) {
        mListener.onResponse(response);
    }
}
