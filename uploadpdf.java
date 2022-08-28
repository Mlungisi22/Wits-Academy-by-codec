package com.example.login;

public class uploadpdf {
    public String pdfname,pdfurl;

    public uploadpdf(){

    }
    public uploadpdf(String pdfname,String pdfurl){
        this.pdfname=pdfname;
        this.pdfurl=pdfurl;
    }

    public String getPdfname(){
        return pdfname;
    }
    public String getPdfurl(){
        return pdfurl;
    }
    public void setPdfname(String pdfname){
        this.pdfname=pdfname;
    }
    public void setPdfurl(String pdfurl){
        this.pdfurl=pdfurl;
    }
}
