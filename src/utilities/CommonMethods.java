package utilities;

import java.util.List;

import com.promocamp.model.PromoCampVO;

public class CommonMethods {
	public static Integer minPrice(List<PromoCampVO> list) {
		if (list.size() == 0)
			return null;
		
		Integer minPrice = list.get(0).getPc_price();
		for (PromoCampVO promoCampVO : list) {
			if (minPrice > promoCampVO.getPc_price())
				minPrice = promoCampVO.getPc_price();
		}
		
		return minPrice;
	}
}