using System.Threading.Tasks;
using Microsoft.AspNetCore.Mvc;
using RecommendationService.Model;
using RecommendationService.Service.RecommendationServices;

namespace RecommendationService.Controllers
{
    [Route("[controller]")]
    [ApiController]
    public class RecommendationController : ControllerBase
    {
        private readonly IRecommendationService _recommendationService;

        public RecommendationController(IRecommendationService recommendationService)
        {
            _recommendationService = recommendationService;
        }

        [HttpGet("{id}")]
        public async Task<IActionResult> GetUserRecommendations(string id)
        {
            return Ok( await _recommendationService.GetUserRecommendations(id));
        }
    }
}


