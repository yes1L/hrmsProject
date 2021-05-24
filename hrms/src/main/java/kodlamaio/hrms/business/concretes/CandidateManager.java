package kodlamaio.hrms.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kodlamaio.hrms.Mernis.UserValidationService;
import kodlamaio.hrms.business.abstracts.CandidateService;
import kodlamaio.hrms.core.utilities.result.DataResult;
import kodlamaio.hrms.core.utilities.result.ErrorResult;
import kodlamaio.hrms.core.utilities.result.Result;
import kodlamaio.hrms.core.utilities.result.SuccessDataResult;
import kodlamaio.hrms.core.utilities.result.SuccessResult;
import kodlamaio.hrms.dataAccess.abstracts.CandidateDao;
import kodlamaio.hrms.entities.concretes.Candidate;

@Service
public class CandidateManager implements CandidateService{
	
	private CandidateDao candidateDao;
	private UserValidationService userValidationService;

	@Autowired
	public CandidateManager(CandidateDao candidateDao, UserValidationService userValidationService) {
		super();
		this.candidateDao = candidateDao;
	}

	@Override
	public DataResult<List<Candidate>> getAll() {
		return new SuccessDataResult<List<Candidate>>(candidateDao.findAll(), "Data listelendi");
	}

	@Override
	public Result add(Candidate candidate) {
		
		if(candidate.getFirstName() == null || 
				candidate.getLastName() == null || 
				candidate.getIdentityNumber() == null || 
				candidate.getBirthDate() == null || 
				candidate.getEmail() == null ||
				candidate.getPassword() == null) {
			return new ErrorResult("Alanlar boş bırakılamaz");
			
		}else if(userValidationService.validate(candidate) == false) {
			return new ErrorResult("Mernis doğrulaması gerçekleşmedi");
			
		}else if(candidateDao.findAllByEmail(candidate.getEmail()).stream().count() != 0) {
			return new ErrorResult("Aynı mail adresi kullanılmaktadır");
			
		}else if(candidateDao.findAllByIdentityNumber(candidate.getIdentityNumber()).stream().count() != 0) {
			return new ErrorResult("Aynı T.C. kimlik numarası kullanılmaktadır");
			
		}else {
			candidateDao.save(candidate);
			return new SuccessResult("Eklendi");
			
		}
		
		
		
	}
}
