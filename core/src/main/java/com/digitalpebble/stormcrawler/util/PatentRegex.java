package com.digitalpebble.stormcrawler.util;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.lang.String;
import java.io.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.yaml.snakeyaml.Yaml;

/**
 *  Patent regex detector to decide whether or not to archive a page/document.
 */

public class PatentRegex {
    
    private static String  patentKeywordRegex = null;
    private static String  patentNumberRegex  = null;

    private static Pattern patternKeyword     = null;
    private static Pattern patternNumber      = null;

    public PatentRegex() {

	System.out.println("Building the regex.");
	
	Yaml yaml = new Yaml();
	
	InputStream in = null;
	Map<String, String> values = null;

	try {
	    in = PatentRegex.class.getResourceAsStream("/patent-regex.yaml");
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>><in = " + PatentRegex.class);
	    values = (Map<String, String>) yaml.load(in);
	} catch (Exception e) {	    
	    System.out.println(e);
	}

	patentNumberRegex  = values.get("patentNumberRegex");
	patentKeywordRegex = values.get("patentKeywordRegex");
	
	if (patentNumberRegex == null) {
	    throw new java.lang.Error("Fatal. Empty patentNumberRegex!");
	}
	if (patentKeywordRegex == null) {
	    throw new java.lang.Error("Fatal. Empty patentKeywordRegex!");
	}
	
	//System.out.println("patentNumberRegex  = " + patentNumberRegex);
	//System.out.println("patentKeywordRegex = " + patentKeywordRegex);

	patternKeyword = Pattern.compile(patentKeywordRegex);
	patternNumber  = Pattern.compile(patentNumberRegex);
    }
    
    public static Pattern getPatternKeyword () {
	return patternKeyword;
    }

    public static Pattern getPatternNumber () {
	return patternNumber;
    }
 
    //private String cleanSpaces( String text ) {
    //	return text.replaceAll("[\\h\\s\\v]+", " ").trim();
    //}


    public Boolean detectPatentMentionIn( String text ) {
	
	text = text.replaceAll("[\\h\\s\\v]+", " ").trim();
	    
	Matcher matcherKeyword = this.patternKeyword.matcher( text );
	Matcher matcherNumber  = this.patternNumber.matcher( text );
	    
	if (matcherKeyword.find() && matcherNumber.find()) {
	    return true;
	}
	
	return false;
    }

    public Boolean detectPatentKeywordMentionIn( String text ) {
	
	text = text.replaceAll("[\\h\\s\\v]+", " ").trim();
	    
	Matcher matcherKeyword = this.patternKeyword.matcher( text );
	    
	if ( matcherKeyword.find() ) {
	    return true;
	}
	
	return false;
    }

}
