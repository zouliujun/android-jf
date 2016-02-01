package cn.change365.framework.network;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by Jack on 2015/6/19.
 */
public interface BaseXmlParser {
    
    public <T> List parse(InputStream is) throws IOException, XmlPullParserException;
}
