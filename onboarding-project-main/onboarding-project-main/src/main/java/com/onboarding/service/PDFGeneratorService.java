package com.onboarding.service;

import java.io.IOException;
import java.util.List;
import com.onboarding.model.UserOnboarding;

public interface PDFGeneratorService {

	public void export(String mentor, List<UserOnboarding> onboardings) throws IOException;
	
}
