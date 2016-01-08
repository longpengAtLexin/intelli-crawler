package intelli.crawler.web.controller;




import intelli.crawler.common.dao.CrawlerTaskInfoDao;
import intelli.crawler.common.dao.IocManager;
import intelli.crawler.common.dao.model.CrawlerTaskInfoPo;
import intelli.crawler.common.dao.model.PagingCrawlerTaskInfoPo;
import intelli.crawler.web.model.PagingModel;


import java.sql.SQLException;
import java.util.List;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class IndexController 
{

	private static Logger log = LoggerFactory.getLogger(IndexController.class);

	private CrawlerTaskInfoDao dao = IocManager.getInstance().getDao("crawlerTaskInfoDao");

	@RequestMapping("/")
	public ModelAndView index(HttpServletRequest request)
	{
		log.info("Redirect to the index.html");
		return new ModelAndView("index.html");
	}

	@ResponseBody
	@RequestMapping("/getCrawlerInfo")
	public PagingModel getCrawlerInfo(HttpServletRequest request ) {
		
		int start = Integer.parseInt(request.getParameter("start")); // 取得当前页数,注意这是jqgrid自身的参数
		int limit = Integer.parseInt(request.getParameter("limit")); // 取得每页显示行数，,注意这是jqgrid自身的参数
		String id = request.getParameter("id");
		String pid = request.getParameter("pid");
		String name = request.getParameter("name");
		String startDate = request.getParameter("startDate");
		String endDate = request.getParameter("endDate");
		

		
		PagingCrawlerTaskInfoPo condition = new PagingCrawlerTaskInfoPo();
		if(!StringUtils.isEmpty(id))
			condition.setId(id);
		if(!StringUtils.isEmpty(pid))
			condition.setPid(pid);
		if(!StringUtils.isEmpty(name))
		{
			condition.setName(name);
		}
		if(!StringUtils.isEmpty(startDate)&&Pattern.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$", startDate))
			condition.setStartDate(startDate+" 00:00:00");
		if(!StringUtils.isEmpty(endDate)&&Pattern.matches("^[0-9]{4}-[0-9]{2}-[0-9]{2}$", endDate))
			condition.setEndDate(endDate+" 23:59:59");
		condition.setStart(start);
		condition.setLimit(limit);	
		/*System.out.println( JSON.toJSONString(request.getParameterMap()) );
		System.out.println( JSON.toJSONString(condition) );*/
		PagingModel pageVo = new PagingModel();
		List<CrawlerTaskInfoPo> lst = null;
		
		try
		{
			int count = dao.count(condition).getCountNum();
			lst = dao.selectPaging(condition);
			pageVo.setResults(count);	
			pageVo.setRows(lst);
			
		} catch (SQLException e1) 
		{
			log.warn("数据库错误,error:", e1);
		}catch (Exception e)
		{
			log.warn("获取爬虫任务列表异常,error:", e);
		}
		
		return pageVo;
	}

}
