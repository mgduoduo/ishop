package com.zerol.crm.common.tag;

import freemarker.core.Environment;
import freemarker.template.*;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * Created by gaoqiang on 30/1/2015.
 */
public class UpperDirective implements TemplateDirectiveModel {

    @Override
    public void execute(Environment env, Map map, TemplateModel[] templateModels,
                        TemplateDirectiveBody body) throws TemplateException, IOException {
        if(!map.isEmpty()){
            throw new TemplateModelException("this directive doesn't allow parameters");
        }
        if(templateModels.length!=0){
            throw new TemplateModelException("this directive doesn't allow variables");
        }
        if(body!=null){
            body.render(new UpperCaseFilterWriter(env.getOut()));
        }else{
            throw new RuntimeException("missing body");
        }
    }



}
class UpperCaseFilterWriter extends Writer {

    private final Writer out;

    public UpperCaseFilterWriter(Writer out) {
        this.out=out;
    }
    @Override
    public void write(char[] cbuf, int off, int len) throws IOException {
        char[] transformedCbuf = new char[len];
        for(int i=0;i<len;i++){
            transformedCbuf[i]=Character.toUpperCase(cbuf[i+off]);
        }
        out.write(transformedCbuf);
    }

    @Override
    public void flush() throws IOException {
        out.flush();
    }

    @Override
    public void close() throws IOException {
        out.close();
    }

}
