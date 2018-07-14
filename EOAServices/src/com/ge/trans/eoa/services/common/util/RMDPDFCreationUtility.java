/**
 * ============================================================
 * File : RMDPDFCreationUtility.java
 * Description : 
 * Package :  com.ge.trans.rmd.utilities
 * Author : iGATE Global Solutions Ltd.
 * Last Edited By :
 * Version : 1.0
 * Created on : 
 * History
 * Modified By : Initial Release
 *
 * Copyright (C) 2009 General Electric Company. All rights reserved
 *
 * ============================================================
 */
package com.ge.trans.eoa.services.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;

import com.ge.trans.rmd.common.constants.RMDCommonConstants;
import com.ge.trans.rmd.common.constants.RMDPdfConstants;
import com.ge.trans.rmd.common.valueobjects.ElementVO;
import com.ge.trans.rmd.common.valueobjects.PdfDetailsVO;
import com.ge.trans.rmd.logging.RMDLogger;
import com.ge.trans.rmd.logging.RMDLoggerHelper;
import com.ge.trans.rmd.utilities.RMDCommonUtility;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.FontSelector;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;

/*******************************************************************************
 * @Author :
 * @Version : 1.0
 * @Date Created: Dec 17, 2009
 * @Date Modified :
 * @Modified By :
 * @Contact :
 * @Description :
 * @History :
 ******************************************************************************/
@SuppressWarnings("unchecked")
public final class RMDPDFCreationUtility {

    private static final RMDLogger LOG = RMDLoggerHelper.getLogger(RMDPDFCreationUtility.class);

    /**
     * private constructor
     */
    private RMDPDFCreationUtility() {
    }

    /**
     * @param objPdfDetailsVO
     * @param strLanguage
     * @param pdffilePath
     * @param pdffileName
     */
    public static synchronized void generatePDFNew(PdfDetailsVO objPdfDetailsVO, String strLanguage, String basePath,
            String pdffilePath, String pdffileName) {
        BaseFont bfchinese = null;
        String Nnote = RMDCommonConstants.EMPTY_STRING;
        String pdfTitle = RMDCommonConstants.EMPTY_STRING;
        FontSelector tablecontents = new FontSelector();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ArrayList headerTitleLst = new ArrayList();
        ArrayList headerLst = new ArrayList();
        ArrayList infoLst = new ArrayList();
        ArrayList taskHdrLst = new ArrayList();
        ArrayList taskDataLst = new ArrayList();
        ArrayList taskRowLst;
        ElementVO objElementVO;
        PdfReader reader = null;
        PdfStamper stamper = null;
        FileOutputStream fileOutputStream = null;
        try {
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, baos);
            document.open();
            FontSelector fontselector = new FontSelector();
            FontSelector smallfontselector = new FontSelector();
            FontSelector innerLetters = new FontSelector();
            FontSelector Labelletters = new FontSelector();
            FontSelector TaskFont = new FontSelector();
            FontSelector fontselectorfooter = new FontSelector();
            FontSelector tablehead = new FontSelector();
            FontSelector recom = new FontSelector();
            if (strLanguage.equals(RMDCommonConstants.ENGLISH_LANGUAGE)) {
                fontselector.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 20));
                smallfontselector.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 9));
                fontselectorfooter.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 7));
                innerLetters.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 8));
                Labelletters.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 8, Font.BOLD));
                tablecontents.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 8));
                tablehead.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 8));
                Font notesfont = new Font(Font.FontFamily.TIMES_ROMAN, 9);
                notesfont.setColor(255, 0, 0);
                recom.addFont(notesfont);
                TaskFont.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 15));
            }
            if (strLanguage.equals(RMDCommonConstants.CHINESE_LANGUAGE)) {
                bfchinese = BaseFont.createFont(basePath + "app/conf/gbsn00lp.ttf", BaseFont.IDENTITY_H,
                        BaseFont.EMBEDDED);
                fontselector.addFont(new Font(bfchinese, 20));
                smallfontselector.addFont(new Font(bfchinese, 9));
                fontselectorfooter.addFont(new Font(bfchinese, 7));
                innerLetters.addFont(new Font(bfchinese, 10));
                Labelletters.addFont(new Font(bfchinese, 10, Font.BOLD));
                tablecontents.addFont(new Font(bfchinese, 8));
                tablehead.addFont(new Font(bfchinese, 8));
                Font notesfont = new Font(bfchinese, 9);
                notesfont.setColor(255, 0, 0);
                recom.addFont(notesfont);
                TaskFont.addFont(new Font(bfchinese, 15));
            }
            /* HEADER TITLE STARTS */

            headerTitleLst = objPdfDetailsVO.getPdfHdrList();
            pdfTitle = RMDCommonUtility.getMessage((String) headerTitleLst.get(0), null, strLanguage);
            Phrase phTitle = fontselector.process(pdfTitle);
            float[] widths1 = { 0.6f, 0.1f, 0.6f };
            PdfPTable otable = new PdfPTable(widths1);
            otable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            otable.setHorizontalAlignment(Element.ALIGN_LEFT);
            otable.getDefaultCell().setFixedHeight(30);
            PdfPCell ocell = new PdfPCell(new Paragraph(phTitle));
            ocell.setBorder(0);
            ocell.setColspan(3);
            ocell.setHorizontalAlignment(Element.ALIGN_CENTER);
            otable.addCell(ocell);
            otable.addCell(RMDCommonConstants.EMPTY_STRING);
            otable.addCell(RMDCommonConstants.EMPTY_STRING);
            otable.addCell(RMDCommonConstants.EMPTY_STRING);
            /* HEADER TITLE ENDS */
            /* HEADER INFO STARTS */
            headerLst = objPdfDetailsVO.getHeaderList();
            int iheaderLstSize = headerLst.size();
            for (int index = 0; index < iheaderLstSize; index++) {
                objElementVO = (ElementVO) headerLst.get(index);
                Phrase lblPhrase = Labelletters
                        .process(RMDCommonUtility.getMessage(objElementVO.getId(), null, strLanguage));

                Phrase valuePhrase = innerLetters.process(objElementVO.getName());
                ocell = new PdfPCell(new Paragraph(lblPhrase));
                ocell.setBorder(0);
                ocell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                otable.addCell(ocell);
                ocell = new PdfPCell(new Paragraph(RMDCommonConstants.EMPTY_STRING));
                ocell.setBorder(0);
                ocell.setFollowingIndent(2);
                otable.addCell(ocell);
                ocell = new PdfPCell(new Paragraph(valuePhrase));
                ocell.setBorder(0);
                ocell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                otable.addCell(ocell);
            }
            document.add(otable);
            /* HEADER INFO ENDS */
            /* INFO STARTS */
            infoLst = objPdfDetailsVO.getInfoList();
            int iInfoLstSize = infoLst.size();
            Phrase recphrase;
            for (int index = 0; index < iInfoLstSize; index++) {
                objElementVO = (ElementVO) infoLst.get(index);
                if (RMDCommonConstants.EMPTY_STRING.equals(objElementVO.getId())) {
                    recphrase = recom.process(objElementVO.getName());
                } else {
                    recphrase = recom.process(RMDCommonUtility.getMessage(objElementVO.getId(), null, strLanguage)
                            + objElementVO.getName());
                }
                document.add(new Paragraph(RMDCommonConstants.NEWLINE));
                document.add(new Paragraph(recphrase));
            }

            String associateTask = RMDCommonUtility.getMessage("PDF_TASK", null, strLanguage);
            Phrase Taskphrase = TaskFont.process(associateTask);
            document.add(new Paragraph(RMDCommonConstants.NEWLINE));
            document.add(new Paragraph(Taskphrase));
            document.add(new Paragraph(RMDCommonConstants.NEWLINE));
            /* INFO ENDS */
            /* TASK HEADER STARTS */
            taskHdrLst = objPdfDetailsVO.getTaskHdrList();
            float[] widths = new float[taskHdrLst.size()];
            for (int i = 0; i < taskHdrLst.size(); i++) {

                switch (i) {

                case 0:
                    widths[i] = 0.3f;
                    break;
                case 1:
                    widths[i] = 1.5f;
                    break;
                default:
                    widths[i] = 0.5f;
                    break;

                }
            }

            PdfPTable headtable = new PdfPTable(widths);
            headtable.setWidthPercentage(100);
            headtable.getDefaultCell().setBorderWidth(2f);
            headtable.getDefaultCell().setBorderColor(BaseColor.BLACK);
            headtable.setHorizontalAlignment(Element.ALIGN_LEFT);
            PdfPCell cell;
            int iTaskHdrLstSize = taskHdrLst.size();
            Phrase taskHeader = null;
            for (int index = 0; index < iTaskHdrLstSize; index++) {
                objElementVO = (ElementVO) taskHdrLst.get(index);
                taskHeader = tablehead.process(RMDCommonUtility.getMessage(objElementVO.getId(), null, strLanguage));

                cell = new PdfPCell(new Paragraph(taskHeader));
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                cell.setBackgroundColor(BaseColor.GRAY);
                cell.setPadding(3.0f);
                headtable.addCell(cell);
            }
            document.add(headtable);
            /* TASK HEADER ENDS */
            /* TASK DATA STARTS */
            PdfPTable bodytable = new PdfPTable(widths);
            bodytable.setWidthPercentage(100);
            bodytable.getDefaultCell().setBorderWidth(1f);
            bodytable.getDefaultCell().setBorderColor(BaseColor.BLACK);
            bodytable.setHorizontalAlignment(Element.ALIGN_LEFT);
            taskDataLst = objPdfDetailsVO.getTaskDtlList();
            int iTaskDataLstSize = taskDataLst.size();
            int taskRowListSize = 0;

            for (int index = 0; index < iTaskDataLstSize; index++) {
                taskRowLst = (ArrayList) taskDataLst.get(index);
                taskRowListSize = taskRowLst.size();

                for (int row = 0; row < taskRowListSize; row++) {

                    if ((String) taskRowLst.get(row) != null)
                        bodytable.addCell(tablecontents.process((String) taskRowLst.get(row)));
                    else
                        bodytable.addCell(tablecontents.process(RMDCommonConstants.EMPTY_STRING));

                }
            }

            document.add(bodytable);
            /* TASK DATA ENDS */
            /* COPYRIGHT STARTS */
            Nnote = RMDCommonConstants.NEWLINE + RMDCommonConstants.NEWLINE
                    + RMDCommonUtility.getMessage("PDF_NOTE", null, strLanguage);
            PdfPTable infotable = new PdfPTable(1);
            infotable.setWidthPercentage(100);
            infotable.getDefaultCell().setBorder(Rectangle.NO_BORDER);
            infotable.setHorizontalAlignment(Element.ALIGN_LEFT);
            Phrase noteph = smallfontselector.process(Nnote);
            PdfPCell infocell1 = new PdfPCell(new Paragraph(noteph));
            infocell1.setBorder(0);
            infotable.addCell(infocell1);
            document.add(infotable);
            document.close();
            /* COPYRIGHT ENDS */
            /* FOOTER STARTS */
            reader = new PdfReader(baos.toByteArray());
            int n = reader.getNumberOfPages();
            fileOutputStream = new FileOutputStream(pdffilePath + File.separator + pdffileName);
            stamper = new PdfStamper(reader, fileOutputStream);
            PdfContentByte pageNum;
            Rectangle rect;
            BaseFont bf = null;
            if (strLanguage.equals(RMDCommonConstants.ENGLISH_LANGUAGE)) {
                bf = BaseFont.createFont(BaseFont.TIMES_ROMAN, "Cp1252", false);
                fontselectorfooter.addFont(new Font(Font.FontFamily.TIMES_ROMAN, 7));
            }
            if (strLanguage.equals(RMDCommonConstants.CHINESE_LANGUAGE)) {
                bf = BaseFont.createFont(basePath + "app/conf/gbsn00lp.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
                fontselectorfooter.addFont(new Font(bfchinese, 7));
            }
            String footertext = RMDCommonUtility.getMessage(RMDPdfConstants.PDF_CONTACT_NUMBER, null, strLanguage);
            String strPage = RMDCommonUtility.getMessage(RMDPdfConstants.PDF_PAGE, null, strLanguage);
            for (int i = 1; i < n + 1; i++) {
                pageNum = stamper.getOverContent(i);
                rect = reader.getPageSizeWithRotation(i);
                pageNum.beginText();
                pageNum.setFontAndSize(bf, 10);
                pageNum.showTextAligned(Element.ALIGN_CENTER, footertext, 240, 32, 0);
                pageNum.showTextAligned(Element.ALIGN_RIGHT, strPage + i, rect.getRight(36f), 32, 0);
                pageNum.endText();
            }

            /* FOOTER ENDS */
        } catch (Exception e) {
            LOG.error("Error generatePDFNew() method in  RMDPDFCreateUtility ", e);
        } finally {
            try {
                if (null != reader) {
                    reader.close();
                }
                if (null != stamper) {
                    stamper.close();
                }
                if (null != fileOutputStream) {
                    fileOutputStream.close();
                }

            } catch (Exception exception) {
                LOG.error("Error while closing the resources" + exception);
            }
        }
    }
}
