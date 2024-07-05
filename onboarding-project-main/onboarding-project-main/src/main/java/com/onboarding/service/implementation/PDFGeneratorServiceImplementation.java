package com.onboarding.service.implementation;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lowagie.text.Document;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;
import com.onboarding.model.OnboardingEntity;
import com.onboarding.model.TaskEntity;
import com.onboarding.model.UserOnboarding;
import com.onboarding.model.UserTask;
import com.onboarding.repository.UserTaskRepository;
import com.onboarding.service.EmailSenderService;
import com.onboarding.service.PDFGeneratorService;
import com.onboarding.util.UserTaskId;


@Service
public class PDFGeneratorServiceImplementation implements PDFGeneratorService{
	
	
	@Autowired 
	private UserTaskRepository utRepo;
	
	@Autowired
	private EmailSenderService mailSender;

	
	public void export(String mentor, List<UserOnboarding> onboardings) throws IOException {
		Document document = new Document(PageSize.A4);
		Long time = System.currentTimeMillis();
		final String fName = time.toString().replaceAll("\\s+", "").replaceAll(":","");
		final String filePath = "src/main/resources/tmp/" + fName + ".pdf";
		FileOutputStream outputStream = new FileOutputStream(filePath);
		File f = new File(filePath);
		
		
		PdfWriter.getInstance(document, outputStream);
		
		System.out.println(mentor + " " + onboardings);
		
		document.open();
		
		
		Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		fontTitle.setSize(18);
		
		
		Paragraph paragraph = new Paragraph(mentor,fontTitle);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		document.add(paragraph);
		
		Font fontParagraph = FontFactory.getFont(FontFactory.HELVETICA);
		fontParagraph.setSize(12);
		
		for(UserOnboarding userOnboarding : onboardings) {
			String userId = userOnboarding.getUser().getEmail();
			OnboardingEntity onboarding = userOnboarding.getOnboarding();
			List<UserTask> tasks = new ArrayList<UserTask>();
			for(TaskEntity task : onboarding.getTasks()) {
				Optional<UserTask> uTask = utRepo.findById(new UserTaskId(task.getId(), userId));
				if(!uTask.isEmpty())
					tasks.add(uTask.get());
			}
			String percentage = calculatePercentage(tasks);
			String info = userOnboarding.getOnboarding().getName() + " " + userOnboarding.getUser().getFirstName() 
					+ " " + userOnboarding.getUser().getLastName() + " " + userOnboarding.getCompleted() + " " + percentage;
			Paragraph paragraph2 = new Paragraph(info ,fontParagraph);
			paragraph2.setAlignment(Paragraph.ALIGN_LEFT);
			document.add(paragraph2);
		}
			
		
		document.close();
		f.delete();
		mailSender.sendMessageWithAttachment(mentor, "Weekly report", "Weekly report in attachment", filePath);
			
		
	}
	
	// RACUNANJE PROCENTA
	public String calculatePercentage(List<UserTask> tasks) {
		DecimalFormat f = new DecimalFormat("##.00");
		Float percentage;
		Float ukBroj=0f;
		Float trenutniBroj=0f;
		for(UserTask task : tasks) {
			if(task.getTask().getParentTask() == null) {
				if(task.getCompleted()) {
					trenutniBroj++;
				}
				ukBroj++;
			}		
		}
		percentage = trenutniBroj/ukBroj * 100;
		return percentage != 0 ? f.format(percentage) : percentage.toString();
	}

}
