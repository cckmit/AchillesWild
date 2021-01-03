package com.achilles.wild.server.tool.data.collect;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 提取数据
 * 
 * @author Pang
 */
public class CollectDataUtil {
	/**
	 * 去除所有空格
	 * @param str
	 * @return
	 */
	public static String removeAllSpace(String str){
		return str.replaceAll("\\s", "");
	}
	
	
	/**
	 * 根据给定的姓氏，截取从给定姓氏开始连续的汉子
	 * @param str
	 * @param surname
	 * @return
	 */
	public static Set<String> collectChineseNameToSet(String str,String surname){
		return collectDataToSet(str, "["+surname+"][\u4e00-\u9fa5]+");
	}
	
	/**
	 * 收集所有的姓氏
	 * @param str
	 * @return
	 */
	public static Set<String> collectSurnameToSet(String str) {
		String rule= "赵|钱|孙|李|周|吴|郑|王|冯|陈|褚|卫|蒋|沈|韩|杨|朱|秦|尤|许|何|吕|施|张|孔|曹|严|华|金|魏|陶|姜|戚|谢|邹|喻|柏|水|窦|章|云|苏|潘|"
				+ "葛|奚|范|彭|郎|鲁|韦|昌|马|苗|凤|花|方|俞|任|袁|柳|酆|鲍|史|唐|费|廉|岑|薛|雷|贺|倪|汤|滕|殷|罗|毕|郝|邬|安|常|乐|于|时|傅|皮|卞|齐|康|伍|余|元|卜|顾|孟|平|黄|和|穆|"
				+ "萧|尹|姚|邵|湛|汪|祁|毛|禹|狄|米|贝|明|臧|计|伏|成|戴|谈|宋|茅|庞|熊|纪|舒|屈|项|祝|董|梁|杜|阮|蓝|闵|席|季|麻|强|贾|路|娄|危|江|童|颜|郭|梅|盛|林|刁|锺|徐|邱|骆|高|夏|"
				+ "蔡|田|樊|胡|凌|霍|虞|万|支|柯|昝|管|卢|莫|经|房|裘|缪|干|解|应|宗|丁|宣|贲|邓|郁|单|杭|洪|包|诸|左|石|崔|吉|钮|龚|程|嵇|邢|滑|裴|陆|荣|翁|荀|羊|於|惠|甄|麴|家|封|芮|羿|"
				+ "储|靳|汲|邴|糜|松|井|段|富|巫|乌|焦|巴|弓|牧|隗|山|谷|车|侯|宓|蓬|全|郗|班|仰|秋|仲|伊|宫|宁|仇|栾|暴|甘|钭|历|戎|祖|武|符|刘|景|詹|束|龙|叶|幸|司|韶|郜|黎|蓟|溥|印|"
				+ "宿|白|怀|蒲|邰|从|鄂|索|咸|籍|赖|卓|蔺|屠|蒙|池|乔|阳|郁|胥|能|苍|双|闻|莘|党|翟|谭|贡|劳|逄|姬|申|扶|堵|冉|宰|郦|雍|却|璩|桑|桂|濮|牛|寿|通|边|扈|燕|冀|僪|浦|尚|农|"
				+ "温|别|庄|晏|柴|瞿|阎|充|慕|连|茹|习|宦|艾|鱼|容|向|古|易|慎|戈|廖|庾|终|暨|居|衡|步|都|耿|满|弘|匡|国|文|寇|广|禄|阙|东|欧|殳|沃|利|蔚|越|夔|隆|师|巩|厍|聂|晁|勾|敖|"
				+ "融|冷|訾|辛|阚|那|简|饶|空|曾|毋|沙|乜|养|鞠|须|丰|巢|关|蒯|相|查|后|荆|红|游|竺|权|逮|盍|益|桓|公|万俟|司马|上官|欧阳|夏侯|诸葛|闻人|东方|赫连|皇甫|尉迟|公羊|澹台|"
				+ "公冶|宗政|濮阳|淳于|单于|太叔|申屠|公孙|仲孙|轩辕|令狐|钟离|宇文|长孙|慕容|司徒|司空|召|有|舜|叶赫那拉|丛|岳|寸|贰|皇|侨|彤|竭|端|赫|实|甫|集|象|翠|狂|辟|典|良|函|"
				+ "芒|苦|其|京|中|夕|之|章佳|那拉|冠|宾|香|果|依尔根觉罗|依尔觉罗|萨嘛喇|赫舍里|额尔德特|萨克达|钮祜禄|他塔喇|喜塔腊|讷殷富察|叶赫那兰|库雅喇|瓜尔佳|舒穆禄|"
				+ "爱新觉罗|索绰络|纳喇|乌雅|范姜|碧鲁|张廖|张简|图门|太史|公叔|乌孙|完颜|马佳|佟佳|富察|费莫|蹇|称|诺|来|多|繁|戊|朴|回|毓|税|荤|靖|绪|愈|硕|牢|买|但|巧|枚|撒|泰|"
				+ "秘|亥|绍|以|壬|森|斋|释|奕|姒|朋|求|羽|用|占|真|穰|翦|闾|漆|贵|代|贯|旁|崇|栋|告|休|褒|谏|锐|皋|闳|在|歧|禾|示|是|委|钊|频|嬴|呼|大|威|昂|律|冒|保|系|抄|定|化|莱|校|么|"
				+ "抗|祢|綦|悟|宏|功|庚|务|敏|捷|拱|兆|丑|丙|畅|苟|随|类|卯|俟|友|答|乙|允|甲|留|尾|佼|玄|乘|裔|延|植|环|矫|赛|昔|侍|度|旷|遇|偶|前|由|咎|塞|"
				+ "敛|受|泷|袭|衅|叔|圣|御|夫|仆|镇|藩|邸|府|掌|首|员|焉|戏|可|智|尔|凭|悉|进|笃|厚|仁|业|肇|资|合|仍|九|衷|哀|刑|俎|仵|圭|夷|徭|蛮|汗|孛|乾|帖|"
				+ "罕|洛|淦|洋|邶|郸|郯|邗|邛|剑|虢|隋|蒿|茆|菅|苌|树|桐|锁|钟|机|盘|铎|斛|玉|线|针|箕|庹|绳|磨|蒉|瓮|弭|刀|疏|牵|浑|恽|势|世|仝|同|蚁|止|戢|睢|"
				+ "冼|种|涂|肖|己|泣|潜|卷|脱|谬|蹉|赧|浮|顿|说|次|错|念|夙|斯|完|丹|表|聊|源|姓|吾|寻|展|出|不|户|闭|才|无|书|学|愚|本|性|雪|霜|烟|寒|少|字|桥|"
				+ "板|斐|独|千|诗|嘉|扬|善|揭|祈|析|赤|紫|青|柔|刚|奇|拜|佛|陀|弥|阿|素|长|僧|隐|仙|隽|宇|祭|酒|淡|塔|琦|闪|始|星|南|天|接|波|碧|速|禚|腾|潮|"
				+ "镜|似|澄|潭|謇|纵|渠|奈|风|春|濯|沐|茂|英|兰|檀|藤|枝|检|生|折|登|驹|骑|貊|虎|肥|鹿|雀|野|禽|飞|节|宜|鲜|粟|栗|豆|帛|官|布|衣|藏|宝|钞|银|"
				+ "门|盈|庆|喜|及|普|建|营|巨|望|希|道|载|声|漫|犁|力|贸|勤|革|改|兴|亓|睦|修|信|闽|北|守|坚|勇|汉|练|尉|士|旅|五|令|将|旗|军|行|奉|敬|恭|仪|"
				+ "母|堂|丘|义|礼|慈|孝|理|伦|卿|问|永|辉|位|让|尧|依|犹|介|承|市|所|苑|杞|剧|第|零|谌|招|续|达|忻|六|鄞|战|迟|候|宛|励|粘|萨|邝|覃|辜|初|楼|"
				+ "城|区|局|台|原|考|妫|纳|泉|老|清|德|卑|过|麦|曲|竹|百|福|言|第五|佟|爱|年|笪|谯|哈|墨|南宫|赏|伯|佴|佘|牟|商|西门|东门|左丘|梁丘|琴|后|况|亢|缑|"
				+ "帅|微生|羊舌|海|归|呼延|南门|东郭|百里|钦|鄢|汝|法|闫|楚|晋|谷梁|宰父|夹谷|拓跋|壤驷|乐正|漆雕|公西|巫马|端木|颛孙|子车|督|仉|司寇|亓官|鲜于|锺离|盖|"
				+ "逯|库|郏|逢|阴|薄|厉|稽|闾丘|公良|段干|开|光|操|瑞|眭|泥|运|摩|伟|铁";
		return collectDataToSet(str,rule);
	}
	
	/**
	 * 一位一位的截取汉字拼接成一个字符串
	 * @param str
	 * @return
	 */
	public static String collectOneChineseToStr(String str) {
		return collectDataToStr(str, "[\u4e00-\u9fa5]{1}");
	}
	
	/**
	 * 一位一位的截取数字拼接成一个字符串
	 * @param str
	 * @return
	 */
	public static String collectOneNumToStr(String str) {
		return collectDataToStr(str, "\\d{1}");
	}
	
	/**
	 * 截取手机号
	 * @param str
	 * @return
	 */
	public static Set<String> collectMobileToList(String str) {
		return collectDataToSet(str, "[1]\\d{10}");
	}
	
	/**
	 * 截取符合规则的字符串拼接成一个字符串
	 * @param str
	 * @param rule
	 * @return
	 */
	public static String collectDataToStr(String str, String rule) {
		Matcher matcher = getMatcher(str, rule);
		StringBuffer sb = new StringBuffer();
		while (matcher.find()) {
			sb.append(matcher.group());
		}
		return sb.toString();
	}
	
	/**
	 * 截取字符串到list
	 * @param str 要处理的字符串
	 * @param rule 正则表达式
	 * @return
	 */
	public static List<String> collectDataToList(String str, String rule) {
		Matcher matcher = getMatcher(str, rule);
		List<String> list = new ArrayList<String>();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}
	
	/**
	 * 截取字符串到set
	 * @param str 要处理的字符串
	 * @param rule 正则表达式
	 * @return
	 */
	public static Set<String> collectDataToSet(String str, String rule) {
		Matcher matcher = getMatcher(str, rule);
		Set<String> set = new HashSet<String>();
		while (matcher.find()) {
			set.add(matcher.group());
		}
		return set;
	}
	
	/**
	 * 获取一个字符串出现的系数
	 * @param str 原始字符串
	 * @param subStr 要查找的字符串
	 * @return
	 */
	public static int getCount(String str, String subStr) {
		Matcher matcher = getMatcher(str, subStr);
		int count=0;
		while (matcher.find()) {
			count++;
		}
		return count;
	}
	
	private static Matcher getMatcher(String str, String rule){
		Pattern pattern = Pattern.compile(rule);
		Matcher matcher = pattern.matcher(str);
		return matcher;
	}
	

}
