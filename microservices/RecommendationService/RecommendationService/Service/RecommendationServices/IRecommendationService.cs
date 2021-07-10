
using System.Collections.Generic;
using System.Threading.Tasks;
using RecommendationService.DTOs;

namespace RecommendationService.Service.RecommendationServices
{
    public interface IRecommendationService
    {
         Task<List<RecommendationDTO>>  GetUserRecommendations(string sourceUserId);
    }
}
