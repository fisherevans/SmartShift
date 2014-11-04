package smarts.apidocker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import org.apache.commons.httpclient.HttpStatus;

public class Compiler {
    private static int TAKE_SUB = 6;
    private static int RETURN_SUB = 8;
    private static String PARAM_REGEX = "_([^_]*)_";
    
	private File _src;
	
	private List<Path> _paths = null;
	
	public static void main(String[] args) throws Exception {
	    Compiler c = new Compiler(new File("res/ad/src"));
	    c.digest();
	    File outFolder = new File("res/ad/out");
        c.vomit(new PrintWriter("res/ad/out/index.html"));
	}
	
	public Compiler(File src) throws Exception {
		_src = src;
		_paths = new ArrayList<>();
		if(!_src.isDirectory())
			throw new Exception("Source must be a folder");
	}
	
	public void digest() {
		digest(_src);
	}
	
	private void digest(File file) {
		if(file.isDirectory()) {
			for(File child:file.listFiles()) {
				if(child.isDirectory())
					digest(child);
				else  if(child.isFile() && child.getName().endsWith(".ad"))
					parseFile(child);
			}
		}
	}
	
	private void parseFile(File file) {
		try {
			if(file.getName().endsWith(".ad")) {
				Scanner scanner = new Scanner(file);
				
				Path path = null;
				MethodBody body = null;
				Response response = null;
                ParseMode mode = ParseMode.Params;
				
				String line, content;
				Integer indentLength;
				while(scanner.hasNextLine()) {
					line = scanner.nextLine();
                    indentLength = getIntentLength(line);
                    content = line.trim();
                    
                    if(0 == indentLength) { // PATH
                        path = new Path(content);
                        _paths.add(path);
                    } else if(2 == indentLength) { // METHOD
                        if(content.equalsIgnoreCase("PARAMS")) {
                            mode = ParseMode.Params;
                        } else {
                            String contentSplit[] = content.split(" - ");
                            Method method = Method.getMethod(contentSplit[0]);
                            body = new MethodBody(contentSplit.length > 1 ? contentSplit[1] : "");
                            path.addMethodBody(method, body);
                        }
                    } else if(4 == indentLength) { // TAKES / RETURNS
                        if(content.equalsIgnoreCase("TAKES"))
                            mode = ParseMode.Takes;
                        else if(content.equalsIgnoreCase("RETURNS"))
                            mode = ParseMode.Returns;
                        else if(content.equalsIgnoreCase("REQUIRES"))
                            mode = ParseMode.Requires;
                        else if(mode == ParseMode.Params) {
                            String contentSplit[] = content.split(" - ");
                            path.addParamDescription(contentSplit[0], contentSplit[1]);
                        }
                    } else if(mode == ParseMode.Takes) { // TAKES CONTENT / RETURNS CODE
                        body.appendTakes(line.substring(TAKE_SUB));
                    } else if(mode == ParseMode.Returns) {
                        if(6 == indentLength) {
                            String contentSplit[] = content.split(" - ");
                            Integer code = new Integer(contentSplit[0]);
                            response = new Response(contentSplit[1]);
                            body.addResponse(code, response);
                        } else if(8 <= indentLength) { // RETURNS CONTENT
                            response.appendExampleResponse(line.substring(RETURN_SUB));
                        }
                    } else if(mode == ParseMode.Requires) {
                        if(6 == indentLength) {
                            body.getRequires().add(content);
                        }
                    }
				}
			} else
				throw new Exception("Invalid extention.");
		} catch(Exception e) {
			System.out.println("Invalid API Docker file: " + (file == null ? "null" : file.getName()));
			e.printStackTrace();
		}
	}
	
	private static int getIntentLength(String str) {
	    int id;
	    for(id = 0;id < str.length() && str.charAt(id) == ' ';id++);
	    return id;
	}
	
	public List<Path> getPaths() {
	    return _paths;
	}
	
	public void vomit(PrintWriter out) {
	    out.println("<!DOCTYPE html><html><head><title>API Documentation</title>");
	    out.println("<link href='style.css' rel='stylesheet' type='text/css' />");
        out.println("<script src='jquery-2.1.1.js'></script><script src='script.js'></script>");
        out.println("<link href='tomorrow.css' rel='stylesheet' type='text/css' /><script src='highlight.pack.js'></script>");
	    out.println("</head><body>");
        for(Path path:_paths) {
            for(Method method:Method.values()) {
                MethodBody body = path.getMethodBody(method);
                if(body == null)
                    continue;
                out.println("  <div class='path box'>");

                String pathLabel = path.getName();
                String aName = path.getName().replaceAll("[^a-zA-Z0-9]", "-").replaceAll("-+", "-").replaceAll("((^-)|(-$))", "") + "-" + method.name().toLowerCase();
                pathLabel = pathLabel.replaceAll(PARAM_REGEX, "<span class='inline-param'>$1</span>");
                out.println("    <div class='path-header'>");
                out.println("      <a name='" + aName + "'></a>");
                out.println("      <div class='path-label'>" + pathLabel + "</div>");
                out.println("      <div class='path-method-label " + method.toString().toLowerCase() + "'>" + method.toString().toUpperCase() + "</div>");
                out.println("      <div class='path-method-description'>" + body.getDescription() + "</div>");
                out.println("    </div>"); // end path-header
                
                out.println("    <div class='path-body hidden'><div class='path-body-content'>");
                
                if(path.getParams().size() > 0) {
                    out.println("      <div class='params box'>");
                    out.println("        <div class='params-label'>URL Parameters</div>");
                    for(String param:path.getParams()) {
                        String paramDesc = path.getParamDescription(param);
                        out.println("          <div class='param box'>");
                        out.println("            <div class='param-label'>" + param + "</div>");
                        out.println("            <div class='param-description'>" + paramDesc + "</div>");
                        out.println("          </div>");
                    }
                    out.println("      </div>"); // end params
                }
                
                if(body.getRequires().size() > 0) {
                    out.println("      <div class='requires box'>");
                    out.println("        <div class='requires-label'>Requires</div>");
                    out.println("          <div class='requires-list'>");
                    String delim = "";
                    for(String requires:body.getRequires()) {
                        out.println(delim + requires);
                        delim = ", ";
                    }
                    out.println("        </div>"); // end requires list
                    out.println("      </div>"); // end requires
                }
                
                if(body.getTakes() != null && body.getTakes().length() > 0) {
                    out.println("      <div class='takes box'>");
                    out.println("        <div class='takes-label'>Takes</div>");
                    out.println("<pre class='takes-content box'><code class='json'>" + body.getTakes() + "</code></pre>");
                    out.println("      </div>"); // end takes
                }
                
                out.println("      <div class='returns box'>");
                out.println("        <div class='returns-label'>Returns</div>");
                for(Integer code:body.getCodes()) {
                    Response response = body.getResponse(code);
                    out.println("        <div class='response " + code + " box'>");
                    out.println("          <div class='response-label'>HTTP " + code + " - " + HttpStatus.getStatusText(code) + "</div>");
                    out.println("          <div class='response-description'>" + response.getDescription() + "</div>");
                    if(response.getExampleResponse() != null && response.getExampleResponse().length() > 0) {
                        out.println("<pre class='response-content box'><code class='json'>" + response.getExampleResponse() + "</code></pre>");
                    }
                    out.println("        </div>"); // end response
                }
                out.println("      </div>"); // end returns
                out.println("    </div></div>"); // end path-body
                out.println("  </div>"); // end path
            }
        }
        out.println("</body></html>");
        out.flush();
	}
	
	private enum ParseMode {
	    Params, Takes, Returns, Requires;
	}
}
