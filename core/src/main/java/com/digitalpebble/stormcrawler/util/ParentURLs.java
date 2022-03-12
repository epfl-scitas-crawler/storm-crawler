package com.digitalpebble.stormcrawler.util;

import java.net.URL;
import java.net.MalformedURLException;
import java.util.ArrayList;

public class ParentURLs {

    
    public static ArrayList<String> getListOfParentUrls(String urls) {
	ArrayList<String> parents = new ArrayList<String>();

	try {
	    URL url = new URL( urls );
	    String   host   = url.getHost();
	    String[] levels = host.split("\\.");
	    String   prot   = url.getProtocol();
	    
	    int l = levels.length;
	    
	    String parent = levels[l-1];
	    
	    for (int i=l-2; 0<=i; i--) {
		parent = levels[i] + "." + parent;
		//System.out.println(" adding new parent " + parent);
		parents.add( prot+"://"+parent );
	    }
	}
	catch(MalformedURLException e) {
	    System.err.println("exception thrown: " + e.getMessage());
	}
	return parents;
    }
}
