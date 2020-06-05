package kr.co.abcmart.common.util;

import javax.servlet.http.HttpServletRequest;

import org.springframework.mobile.device.Device;
import org.springframework.mobile.device.DevicePlatform;
import org.springframework.mobile.device.DeviceUtils;
import org.springframework.mobile.device.LiteDeviceResolver;

import kr.co.abcmart.common.constant.BaseCommonCode;
import kr.co.abcmart.common.constant.BaseConst;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UtilsDevice extends org.springframework.mobile.device.DeviceUtils {

	private static final LiteDeviceResolver deviceResolver = new LiteDeviceResolver();

	public static Device getDevice(HttpServletRequest request) {
		// User-Agent 로 PC/Mobile 을 판단
		Device device = DeviceUtils.getCurrentDevice(request);
		//log.error("##################### UTIL DEVICE ###########"+device+".");
		if (device == null) {
			device = deviceResolver.resolveDevice(request);
			//log.error("##################### NULL DEVICE ###########"+device+".");
		}
		return device;
	}

	// 뷰어에 따른 디바이스 변경
	private static boolean isViewer(HttpServletRequest request, String device) {
		if (!UtilsText.isBlank(request.getParameter("viewer"))) {
			return UtilsText.equals(request.getParameter("viewer").toLowerCase(), device);
		}
		return false;
	}

	public static boolean isMobile(HttpServletRequest request) {
		if (isViewer(request, BaseConst.VIEWER_DEVICE_PC)) {
			return false;
		} else if (isViewer(request, BaseConst.VIEWER_DEVICE_MOBILE)) {
			return true;
		}

		return getDevice(request).isMobile();
	}

	public static boolean isPc(HttpServletRequest request) {
		if (isViewer(request, BaseConst.VIEWER_DEVICE_PC)) {
			return true;
		} else if (isViewer(request, BaseConst.VIEWER_DEVICE_MOBILE)) {
			return false;
		}

		return getDevice(request).isNormal();
	}

	public static boolean isTablet(HttpServletRequest request) {
		if (isViewer(request, BaseConst.VIEWER_DEVICE_PC)) {
			return true;
		} else if (isViewer(request, BaseConst.VIEWER_DEVICE_MOBILE)) {
			return false;
		}
		return getDevice(request).isTablet();
	}

	public static DevicePlatform getDevicePlatform(HttpServletRequest request) {
		return getDevice(request).getDevicePlatform();
	}

	public static boolean isApp(HttpServletRequest request) {
		return isAppAOS(request) || isAppIOS(request);
	}

	public static boolean isAppIOS(HttpServletRequest request) {
		String userAgent = gerUserAgent(request);

		return userAgent.contains(BaseConst.APP_IOS);
	}

	public static boolean isAppAOS(HttpServletRequest request) {
		String userAgent = gerUserAgent(request);

		return userAgent.contains(BaseConst.APP_AOS);
	}

	public static boolean isFace(HttpServletRequest request) {
		String userAgent = gerUserAgent(request);

		return userAgent.contains(BaseConst.APP_IOS_FACE);
	}

	public static boolean isFinger(HttpServletRequest request) {
		String userAgent = gerUserAgent(request);

		return userAgent.contains(BaseConst.APP_AOS_FINGER) || userAgent.contains(BaseConst.APP_IOS_FINGER);
	}

	public static String gerUserAgent(HttpServletRequest request) {
		return request.getHeader("User-Agent");
	}

	// 디바이스 코드 가져오기
	public static String getDeviceCode() {
		String deviceCode = "";
		if (UtilsDevice.isPc(UtilsRequest.getRequest())) {
			deviceCode = BaseCommonCode.DEVICE_PC;
		} else if (UtilsDevice.isMobile(UtilsRequest.getRequest())) {
			if (UtilsDevice.isApp(UtilsRequest.getRequest())) {
				deviceCode = BaseCommonCode.DEVICE_APP;
			} else {
				deviceCode = BaseCommonCode.DEVICE_MOBILE;
			}
		} else if (UtilsDevice.isTablet(UtilsRequest.getRequest())) {
			if (UtilsDevice.isApp(UtilsRequest.getRequest())) {
				deviceCode = BaseCommonCode.DEVICE_APP;
			} else {
				deviceCode = BaseCommonCode.DEVICE_MOBILE;
			}
		}else {
			deviceCode = BaseCommonCode.DEVICE_MOBILE;
		}
		//log.debug("######### DEVICECODE ######::::"+ deviceCode);
		//log.error("######### DEVICECODE ######::::"+ deviceCode);
		return deviceCode;
	}

	/**
	 * @Desc : 장치코드가 PC가 아닌지 확인. 모바일/APP인 경우 true 반환
	 * @Method Name : isNotPc
	 * @Date : 2019. 8. 27.
	 * @Author : tennessee
	 * @param deviceCode 장치코드
	 * @return
	 */
	public static boolean isNotPc(String deviceCode) {
		return !UtilsText.equals(BaseCommonCode.DEVICE_PC, deviceCode);
	}
}
