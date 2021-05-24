package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.business.abstracts.EmployerService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.ErrorResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.core.utilities.result.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.EmployerDao;
import kodlamaio.hrms.entities.concretes.Employer;

@Service
public class EmployerManager implements EmployerService{
	
	private EmployerDao employerDao;

	@Autowired
	public EmployerManager(EmployerDao employerDao) {
		super();
		this.employerDao = employerDao;
	}

	@Override
	public DataResult<List<Employer>> getAll() {
		return new SuccessDataResult<List<Employer>>(employerDao.findAll(), "Data listelendi");
	}

	@Override
	public Result add(Employer employer) {
		
		
		
		if(employer.getEmail() == null || 
				employer.getCompanyName() == null || 
				employer.getPassword() == null ||
				employer.getWebAddress() == null  || 
				employer.getPhoneNumber() == null) {
			return new ErrorResult("Alanlar boş bırakılamaz");
			
		}else if(employer.getPassword().length()<6){
			return new ErrorResult("Şifre 6 karakterden az olamaz");
			
		}else if(employer.getEmail().contains(employer.getCompanyName()) == false) {
			return new ErrorResult("Aynı domaine sahip mail adresi kullanılmalıdır");
			
		}else if(employerDao.findAllByEmail(employer.getEmail()).stream().count() != 0) {
			return new ErrorResult("Aynı mail adresi kullanılmaktadır");
			
		}else {
			employerDao.save(employer);
			return new SuccessResult("Eklendi");
			
		}
		
	}

}
