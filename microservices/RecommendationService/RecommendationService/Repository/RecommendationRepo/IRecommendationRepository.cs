
using System.Collections.Generic;
using System.Threading.Tasks;
using RecommendationService.DTOs;

namespace RecommendationService.Repository.RecommendationRepo
{
    public interface IRecommendationRepository
    {
        Task<List<RecommendationDTO>> GetUserRecommendations(string sourceUserId);
    }
}
