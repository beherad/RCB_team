package com.team;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

public class TeamValidate extends Generic{
	
	ObjectMapper objm=new ObjectMapper();
	
	@Test
	public void validateTeamWithForigenPlayer() throws IOException{
		
		int count=0;
		for(Object forg:getplayerdetails(resourcespath+"/"+filepath,"$.player[*].country")) {
			if(!forg.toString().equalsIgnoreCase("india")) {
				count=count+1;
			}
		}
		if(count<=4) {
			ReportPassEvent("RCB team has "+count+" foreign players");
		}else {
			ReportFailEvent("RCB team has "+count+" foreign players");
		}
	}
	@Test
	public void validateAtleastOneWK() throws IOException {
		List<Object> wk=getplayerdetails(resourcespath+"/"+filepath,"$.player[*].role");
		if(wk.toString().contains("Wicket-keeper")) {
			ReportPassEvent("RCB team has Wicket-keeper");
		}else {
			ReportFailEvent("RCB team doesnot have  Wicket-keeper");
		}
		
	}
	
	public  String readFile(String path, Charset encoding)
            throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
	public List<Object> getplayerdetails(String filepath,String jsonpath) throws IOException{
		List<Object> li=new ArrayList<>();
		String json = readFile(filepath,
                Charset.defaultCharset());
		Object jsonPathArray= JsonPath.read(json,jsonpath);
		li=objm.convertValue(jsonPathArray, new TypeReference<List<String>>() {});
		return li;
	}
}
