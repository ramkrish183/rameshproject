<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
	xmlns:ps="http://schemas.microsoft.com/powershell/2004/04">

	<xsl:template match="WHITE_PAPER_PDF_REPORT">
		<xsl:variable name="geLogoPath">
			<xsl:value-of select="GE_LOGO_PATH" />
		</xsl:variable>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column column-width="1.4cm" />
				<fo:table-column column-width="10cm" />
				<fo:table-column column-width="8cm" />
				<fo:table-body>
					<fo:table-row>
						<fo:table-cell padding-top="0pt">
							<fo:block text-align="start">
								<fo:external-graphic src="{$geLogoPath}"
									content-height="scale-to-fit" height="1.55cm" content-width="1.55cm"
									scaling="uniform" />
							</fo:block>
						</fo:table-cell>
						<fo:table-cell padding-top="8pt">
							<fo:block text-align="start" font-size="16pt"
								line-height="16pt" font-family="calibri" font-weight="bold">
								<xsl:text>&#160;&#160;</xsl:text>
								<xsl:text>GE Global Peformance Optimization &#160; &#160; &#160; &#160;&#160;Center (GPOC) Rx Change Notice</xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell padding-top="14pt">
							<fo:block text-align="start" font-size="9pt"
								line-height="9pt" font-family="calibri">
								&#160; &#160; &#160; &#160; &#160; &#160;&#160; &#160;
								&#160;&#160; &#160;&#160;&#160; &#160;&#160;&#160; &#160;Issue Date:
								<xsl:value-of select="ISSUE_DATE" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<!-- <fo:block white-space-collapse="false" white-space-treatment="preserve" 
			font-size="0pt" line-height="2px">.</fo:block> -->

		<fo:block border-bottom-width="2.5pt" border-bottom-style="solid"
			margin-top="5.5mm" background-color="#cccccc"></fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="2px">.</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>

		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column column-width="4cm" />
				<fo:table-column column-width="13cm" border="inherit" />

				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								font-weight="bold" line-height="9pt" font-family="calibri">
								<xsl:text>New or Existing Rx: </xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								line-height="9pt" font-family="calibri">

								<xsl:value-of select="EXISTING_OR_NEW_RX" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>


		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column column-width="4cm" />
				<fo:table-column column-width="13cm" border="inherit" />

				<fo:table-body>
					<xsl:for-each select="RX_TITLES/RX_TITLES_IMPACTED">
						<xsl:sort select="." />
						<xsl:if
							test="(count(//RX_TITLES/RX_TITLES_IMPACTED) &gt; 6) and ((position( )) &lt; 4)">
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="start" font-size="9pt"
										font-weight="bold" line-height="9pt" font-family="calibri">
										<xsl:if test="(position( )) = 1">
											<xsl:text>Rx Title(s) Impacted: </xsl:text>
										</xsl:if>
									</fo:block>
								</fo:table-cell>

								<fo:table-cell>
									<fo:block text-align="start" font-size="9pt"
										line-height="9pt" font-family="calibri">
										<xsl:value-of select="." />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:if>
						<xsl:if test="(count(//RX_TITLES/RX_TITLES_IMPACTED) &lt; 7)">
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="start" font-size="9pt"
										font-weight="bold" line-height="9pt" font-family="calibri">
										<xsl:if test="(position( )) = 1">
											<xsl:text>Rx Title(s) Impacted: </xsl:text>
										</xsl:if>
									</fo:block>
								</fo:table-cell>

								<fo:table-cell>
									<fo:block text-align="start" font-size="9pt"
										line-height="9pt" font-family="calibri">
										<xsl:value-of select="." />
										,

									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:if>
					</xsl:for-each>
				</fo:table-body>

			</fo:table>
		</fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>

		<fo:block>
			<xsl:if test="(count(//RX_TITLES/RX_TITLES_IMPACTED) &gt; 6)">
				<fo:table border="1pt solid white">
					<fo:table-column column-width="4cm" />
					<fo:table-column column-width="13cm" border="inherit" />
					<fo:table-body>
						<fo:table-row>
							<fo:table-cell>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="start" font-size="9pt"
									line-height="9pt" font-family="calibri">
									<xsl:text>*****</xsl:text>
								</fo:block>
								<fo:block text-align="start" font-size="9pt"
									line-height="9pt" font-family="calibri">
									<xsl:value-of select="RXTITLENOTE" />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</fo:table-body>
				</fo:table>
			</xsl:if>
		</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column column-width="4cm" />
				<fo:table-column column-width="13cm" border="inherit" />
				<fo:table-body>
					<xsl:for-each select="TYPE_OF_CHANGE/CHANGE_TYPE">
						<fo:table-row>
							<fo:table-cell>
								<fo:block text-align="start" font-size="9pt"
									font-weight="bold" line-height="9pt" font-family="calibri">
									<xsl:if test="(position( )) = 1">
										<xsl:text>Type of Change(s): </xsl:text>
									</xsl:if>
								</fo:block>
							</fo:table-cell>
							<fo:table-cell>
								<fo:block text-align="start" font-size="9pt"
									line-height="9pt" font-family="calibri">
									<xsl:value-of select="." />
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:for-each>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column column-width="4cm" />
				<fo:table-column column-width="13cm" border="inherit" />

				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								font-weight="bold" line-height="9pt" font-family="calibri">
								<xsl:text>Affected Models: </xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell wrap-option="wrap">
							<fo:block text-align="start" font-size="9pt"
								line-height="9pt" font-family="calibri" wrap-option="wrap"
								linefeed-treatment="preserve" white-space-treatment="preserve">								
								<xsl:value-of select="AFFECTED_MODELS" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column column-width="4cm" />
				<fo:table-column column-width="13cm" border="inherit" />

				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								font-weight="bold" line-height="9pt" font-family="calibri">
								<xsl:text>GE Request ID: </xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								line-height="9pt" font-family="calibri">

								<xsl:value-of select="GE_REQUEST_ID" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column column-width="4cm" />
				<fo:table-column column-width="13cm" border="inherit" />

				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								font-weight="bold" line-height="9pt" font-family="calibri">
								<xsl:text>Requested By: </xsl:text>
							</fo:block>
						</fo:table-cell>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								line-height="9pt" font-family="calibri">

								<xsl:value-of select="REQUESTED_BY" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="6px">.</fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column />
				<fo:table-body>
					<fo:table-row background-color="#e6e6e6">
						<fo:table-cell>
							<fo:block text-align="start" font-size="13pt"
								font-weight="bold" line-height="16pt" font-family="calibri">
								<xsl:text>Summary </xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column />

				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								line-height="9pt" font-family="calibri" linefeed-treatment="preserve"
								white-space-treatment="preserve" white-space-collapse="false">
								<xsl:value-of select="SUMMARY" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="6px">.</fo:block>

		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-column />

				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								font-weight="bold" line-height="9pt" font-family="calibri">
								<xsl:value-of select="TRIGGER_LOGIC_TEXT" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>
		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="6px">.</fo:block>


		<fo:block>
			<fo:table border="1pt solid white">

				<fo:table-body>
					<fo:table-row background-color="#e6e6e6">
						<fo:table-cell>
							<fo:block text-align="start" font-size="13pt"
								font-weight="bold" line-height="16pt" font-family="calibri">
								<xsl:text>Requested Changes: </xsl:text>
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block>
			<fo:table border="1pt solid white">

				<fo:table-body>
					<fo:table-row>
						<fo:table-cell>
							<fo:block text-align="start" font-size="9pt"
								line-height="9pt" font-family="calibri" linefeed-treatment="preserve"
								white-space-treatment="preserve" white-space-collapse="false">
								<xsl:value-of select="REQUESTED_CHANGES" />
							</fo:block>
						</fo:table-cell>
					</fo:table-row>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="2px">.</fo:block>

		<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />


		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-body>
					<xsl:if test="(count(//RX_TITLES/RX_TITLES_IMPACTED) &gt; 6)">
						<fo:table-row background-color="#e6e6e6">
							<fo:table-cell>
								<fo:block text-align="start" font-size="13pt"
									font-weight="bold" line-height="16pt" font-family="calibri">
									<xsl:text>Rx Titles Impacted (</xsl:text>
									<xsl:value-of select="(count(//RX_TITLES/RX_TITLES_IMPACTED))" />
									<xsl:text>)</xsl:text>
								</fo:block>
							</fo:table-cell>
						</fo:table-row>
					</xsl:if>
				</fo:table-body>
			</fo:table>
		</fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="4px">.</fo:block>
		<fo:block>
			<fo:table border="1pt solid white">
				<fo:table-body>
					<xsl:for-each select="RX_TITLES/RX_TITLES_IMPACTED">
						<xsl:sort select="." />
						<xsl:if
							test="(count(//RX_TITLES/RX_TITLES_IMPACTED) &gt; 6) and ((position( )) &gt; 3)">
							<fo:table-row>
								<fo:table-cell>
									<fo:block text-align="start" font-size="9pt"
										line-height="9pt" font-family="calibri">
										<xsl:text>&#160; &#160;</xsl:text>
										<xsl:value-of select="." />
									</fo:block>
								</fo:table-cell>
							</fo:table-row>
						</xsl:if>
					</xsl:for-each>
				</fo:table-body>

			</fo:table>
		</fo:block>

		<fo:block white-space-collapse="false" white-space-treatment="preserve"
			font-size="0pt" line-height="2px">.</fo:block>
	</xsl:template>

	<xsl:template match="PAGEHEADER">
		<xsl:variable name="geLogoPath">
            <xsl:value-of select="GE_LOGO_PATH" />
        </xsl:variable>
        <fo:block>
            <fo:table border="1pt solid white">
                <fo:table-column column-width="1.4cm" />
                <fo:table-column column-width="10cm" />
                <fo:table-column column-width="8cm" />
                <fo:table-body>
                    <fo:table-row>
                        <fo:table-cell padding-top="0pt">
                            <fo:block text-align="start">
                                <fo:external-graphic src="{$geLogoPath}"
                                    content-height="scale-to-fit" height="1.55cm" content-width="1.55cm"
                                    scaling="uniform" />
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding-top="8pt">
                            <fo:block text-align="start" font-size="16pt"
                                line-height="16pt" font-family="calibri">
                                <xsl:text>&#160;&#160;</xsl:text>
                                <xsl:text>GE Global Peformance Optimization &#160; &#160; &#160; &#160;&#160;Center (GPOC) Rx Change Notice</xsl:text>
                            </fo:block>
                        </fo:table-cell>
                        <fo:table-cell padding-top="14pt">
                            <fo:block text-align="start" font-size="9pt"
                                line-height="9pt" font-family="calibri">
                                &#160; &#160; &#160; &#160; &#160; &#160;&#160; &#160;
                                &#160;&#160; &#160;Issue Date:
                                <xsl:value-of select="ISSUE_DATE" />
                            </fo:block>
                        </fo:table-cell>
                    </fo:table-row>
                </fo:table-body>
            </fo:table>
        </fo:block>
        <fo:block border-bottom-width="2.5pt" border-bottom-style="solid"
            margin-top="5.5mm" background-color="#cccccc"></fo:block>

        <fo:block white-space-collapse="false" white-space-treatment="preserve"
            font-size="0pt" line-height="2px">.</fo:block>
        <fo:block white-space-collapse="false" white-space-treatment="preserve"
            font-size="0pt" line-height="4px">.</fo:block>
	</xsl:template>

	<xsl:template match="COPYRIGHT">
		<fo:block text-align="start" font-size="6pt" line-height="8pt"
			font-family="calibri">
			<xsl:value-of select="." />
		</fo:block>
	</xsl:template>

	<xsl:template match="LINE">
		<fo:block text-align="start" font-size="14pt" line-height="37pt"
			font-family="calibri" space-after.optimum="18pt">
			<xsl:value-of select="asdasd" />
		</fo:block>
	</xsl:template>

	<xsl:template match="imagedata">
		<fo:block>
			<fo:inline-graphic content-height="auto"
				content-width="auto" href="file:{@geLogoPath}" />
		</fo:block>
	</xsl:template>


	<xsl:template match="/">
		<fo:root xmlns:fo="http://www.w3.org/1999/XSL/Format">

			<fo:layout-master-set>
				<fo:simple-page-master master-name="all-pages"
					page-height="25cm" page-width="20cm" margin-top="1cm"
					margin-bottom="1cm" margin-left="1.5cm" margin-right="1cm">
					<fo:region-body margin-top="1.5cm" margin-bottom="2.4cm" />
					<fo:region-before region-name="xsl-region-before" extent="5cm" />
					<fo:region-after region-name="xsl-region-after" extent="2cm" />
				</fo:simple-page-master>

				<fo:page-sequence-master master-name="default-sequence">
					<fo:repeatable-page-master-reference
						master-reference="all-pages" />
				</fo:page-sequence-master>
			</fo:layout-master-set>

			<fo:page-sequence master-reference="default-sequence">
				<fo:static-content flow-name="xsl-region-before">
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/PAGEHEADER" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
				</fo:static-content>
				<fo:static-content flow-name="xsl-region-after">
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/COPYRIGHT" />
					<xsl:apply-templates select="//WHITE_PAPER_PDF_REPORT/LINE" />

					<fo:block font-size="8pt" text-align="right" font-family="calibri">
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;  </xsl:text>
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;  </xsl:text>
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;  </xsl:text>
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;  </xsl:text>
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;  </xsl:text>
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160; &#160;  </xsl:text>
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160;</xsl:text>
						<xsl:text>&#160; &#160; &#160; &#160; &#160; &#160; &#160;   </xsl:text>
							Page :
							<fo:page-number />
							of
							<fo:page-number-citation ref-id="TheVeryLastPage" />
						
					</fo:block>
				</fo:static-content>

				<fo:flow flow-name="xsl-region-body">
					<xsl:apply-templates select="/WHITE_PAPER_PDF_REPORT" />
					<fo:block id="TheVeryLastPage">
					</fo:block>
				</fo:flow>
			</fo:page-sequence>
		</fo:root>
	</xsl:template>


</xsl:stylesheet>