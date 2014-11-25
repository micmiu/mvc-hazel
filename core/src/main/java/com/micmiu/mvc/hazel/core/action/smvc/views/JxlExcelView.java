package com.micmiu.mvc.hazel.core.action.smvc.views;

import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.format.Alignment;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableWorkbook;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import com.micmiu.mvc.hazel.core.common.Reflections;

/**
 * JXL 导出Excel
 * 
 * @author <a href="http://www.micmiu.com">Michael Sun</a>
 * 
 */
public class JxlExcelView extends AbstractJExcelView {
	@SuppressWarnings("unchecked")
	@Override
	protected void buildExcelDocument(Map<String, Object> model,
			WritableWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String fileName = null == model.get(ViewConstant.EXPORT_FILENAME) ? "exportinfo.xls"
				: model.get(ViewConstant.EXPORT_FILENAME) + "";
		if (!fileName.toLowerCase().endsWith(".xls")) {
			fileName += ".xls";
		}
		if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
			fileName = URLEncoder.encode(fileName, "UTF-8");
		} else {
			fileName = new String(fileName.getBytes("utf-8"), "ISO-8859-1");
		}
		response.setHeader("Content-Disposition", "attachment; filename="
				+ fileName);
		String sheetName = null == model.get(ViewConstant.EXPORT_SHEETNAME) ? "info"
				: model.get(ViewConstant.EXPORT_SHEETNAME) + "";
		Map<String, String> showMap = (LinkedHashMap<String, String>) model
				.get(ViewConstant.EXPORT_COLUMN_MAP);
		// 创建工作表
		jxl.write.WritableSheet ws = workbook.createSheet(sheetName, 0); // sheet名称
		int rowIndex = 0;
		// 添加列名
		int columnIndex = 0;
		WritableCellFormat cellFmt = this.getColumnNameFmt();
		for (Map.Entry<String, String> entry : showMap.entrySet()) {
			ws.addCell(new Label(columnIndex++, rowIndex, entry.getValue(),
					cellFmt));
		}
		// 填充数据
		List<Object> dataList = (List<Object>) model
				.get(ViewConstant.EXPORT_ROW_DATA);
		for (Object data : dataList) {
			rowIndex++;
			columnIndex = 0;
			for (Map.Entry<String, String> entry : showMap.entrySet()) {
				ws.addCell(new Label(columnIndex++, rowIndex, Reflections
						.invokeGetter(data, entry.getKey()) + ""));
			}
		}

	}

	/**
	 * 设置列名样式
	 * 
	 * @return
	 */
	private WritableCellFormat getColumnNameFmt() {
		WritableCellFormat cellFmt = null;
		try {
			WritableFont font = new WritableFont(WritableFont.ARIAL,
					WritableFont.DEFAULT_POINT_SIZE, WritableFont.BOLD);

			cellFmt = new WritableCellFormat(font);
			cellFmt.setWrap(true);
			cellFmt.setAlignment(Alignment.CENTRE);
			cellFmt.setVerticalAlignment(VerticalAlignment.CENTRE);

			return cellFmt;
		} catch (Exception e) {
			logger.error("jxl format error:", e);
			return null;
		}
	}

}
