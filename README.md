# web-crawler

Web-Crawler has been built using bare minimum components
1. HTTP Fetcher
2. Page Extractor
3. Policy - PageFetch, Revisit
4. Visited URL eliminator
5. DataStore

For parsing the page, Jsoup library (http://jsoup.org/) has been used to parse the HTML.

The implementation given here is different from architecture in terms of
1. In-process LinkedBlockingQueue has been used as message transfer mechanism between components
2. Persistence is provided by in-memory instance of HSQLDB
3. Mock implementation of indexer and Page caching components

![](https://github.com/manishonline/web-crawler/blob/master/src/docs/WebCrawler_Component_Architecture.pdf)
