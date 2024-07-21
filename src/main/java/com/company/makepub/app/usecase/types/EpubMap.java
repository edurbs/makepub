package com.company.makepub.app.usecase.types;

import java.util.UUID;

public enum EpubMap {

    COVER("OEBPS/Text/cover.xhtml", """
            <?xml version="1.0" encoding="UTF-8" standalone="no" ?>
            <!DOCTYPE html>
            <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops">
            <head>
              <title>Cover</title>
            </head>
            <body>
              <div style="height: 100vh; text-align: center; padding: 0pt; margin: 0pt;">
                <svg xmlns="http://www.w3.org/2000/svg" height="100%" preserveAspectRatio="xMidYMid meet" version="1.1" viewBox="0 0 1654 2339" width="100%" xmlns:xlink="http://www.w3.org/1999/xlink">
                  <image width="1654" height="2339" xlink:href="../Images/cover.png"/>
                </svg>
              </div>
            </body>
            </html>
            """),
    NAV("OEBPS/Text/nav.xhtml","""
            <?xml version="1.0" encoding="utf-8"?>
            <!DOCTYPE html>
            <html xmlns="http://www.w3.org/1999/xhtml" xmlns:epub="http://www.idpf.org/2007/ops" lang="en" xml:lang="en">
            <head>
              <title>ePub NAV</title>
              <meta charset="utf-8"/>
              <link href="../Styles/sgc-nav.css" rel="stylesheet" type="text/css"/>
            </head>
            <body epub:type="frontmatter">
              <nav epub:type="toc" id="toc" role="doc-toc">
                <h1>Sumário</h1>
                <ol>
                  <li>
                    <a href="Section0001.xhtml">Início</a>
                  </li>
                </ol>
              </nav>
              <nav epub:type="landmarks" id="landmarks" hidden="">
                <h1>Landmarks</h1>
                <ol>
                  <li>
                    <a epub:type="toc" href="#toc">Sumário</a>
                  </li>
                  <li>
                    <a epub:type="cover" href="cover.xhtml">Cover</a>
                  </li>
                </ol>
              </nav>
            </body>
            </html>
            """),
    TEXT("OEBPS/Text/Section0001.xhtml",""),
    MUSIC("OEBPS/Music/Section0002.xhtml",""),
    SCRIPTURES("OEBPS/Scriptures/Section0003.xhtml", ""),
    IMAGE("OEBPS/Images/cover.png", ""),
    STYLE("OEBPS/sgc-nav.css", """
            nav#landmarks {
                display:none;
            }
            nav#page-list {
                display:none;
            }
            ol {
                list-style-type: none;
            }
            """),
    CONTENT("OEBPS/content.opf", """
            <?xml version="1.0" encoding="utf-8"?>
            <package version="3.0" unique-identifier="BookId" xmlns="http://www.idpf.org/2007/opf">
              <metadata xmlns:dc="http://purl.org/dc/elements/1.1/" xmlns:opf="http://www.idpf.org/2007/opf">
                <dc:language>xav</dc:language>
                <meta property="dcterms:modified">2024-07-21T15:57:48Z</meta>
                <dc:identifier id="BookId">urn:uuid:%s]</dc:identifier>
                <meta name="cover" content="capa_6.png"/>
              </metadata>
              <manifest>
                <item id="Section0001.xhtml" href="Text/Section0001.xhtml" media-type="application/xhtml+xml"/>
                <item id="Section0002.xhtml" href="Text/Section0002.xhtml" media-type="application/xhtml+xml"/>
                <item id="Section0003.xhtml" href="Text/Section0003.xhtml" media-type="application/xhtml+xml"/>
                <item id="sgc-nav.css" href="Styles/sgc-nav.css" media-type="text/css"/>
                <item id="nav.xhtml" href="Text/nav.xhtml" media-type="application/xhtml+xml" properties="nav"/>
                <item id="cover.png" href="Images/cover.png" media-type="image/png"/>
                <item id="cover.xhtml" href="Text/cover.xhtml" media-type="application/xhtml+xml" properties="svg"/>
              </manifest>
              <spine>
                <itemref idref="cover.xhtml"/>
                <itemref idref="Section0001.xhtml"/>
                <itemref idref="Section0002.xhtml"/>
                <itemref idref="Section0003.xhtml"/>
                <itemref idref="nav.xhtml" linear="no"/>
              </spine>
            </package>
            """);

    private final String path;
    private final String defaultText;

    EpubMap(String path, String defaultText) {
        this.path = path;
        this.defaultText = defaultText;
    }

    public String getPath() {
        return path;
    }

    public String getDefaultText() {
        return defaultText;
    }
}
