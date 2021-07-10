using System;
using System.Collections.Generic;
using System.Linq;
using System.Threading.Tasks;
using RecommendationService.DTOs;
using RecommendationService.Repository.RecommendationRepo;

namespace RecommendationService.Service.RecommendationServices
{
    public class RecommendationService : IRecommendationService
    {
        private readonly IRecommendationRepository _recommendationRepository;

        public RecommendationService(IRecommendationRepository recommendationService)
        {
            _recommendationRepository = recommendationService;
        }

        public async Task<List<RecommendationDTO>> GetUserRecommendations(string sourceUserId)
        {
            return await _recommendationRepository.GetUserRecommendations(sourceUserId);
        }
    }
}
