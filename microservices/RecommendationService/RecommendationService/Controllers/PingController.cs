using System.ComponentModel;
using Microsoft.AspNetCore.Mvc;
using RecommendationService.Model;

namespace RecommendationService.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class PingController : ControllerBase
    {
        [HttpGet]
        public IActionResult Ping()
        {
            return Ok("Successfully pinged.");
        }

    }
}