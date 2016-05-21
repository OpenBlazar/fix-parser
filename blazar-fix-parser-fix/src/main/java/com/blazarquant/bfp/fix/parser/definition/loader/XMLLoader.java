package com.blazarquant.bfp.fix.parser.definition.loader;

import com.blazarquant.bfp.fix.data.definition.FixDictionary;

import java.io.File;
import java.io.InputStream;
import java.util.Map;

/**
 * @author Wojciech Zankowski
 */
public interface XMLLoader {

    Map<Integer, FixDictionary> parseDocument(InputStream documentFile) throws Exception;

}
