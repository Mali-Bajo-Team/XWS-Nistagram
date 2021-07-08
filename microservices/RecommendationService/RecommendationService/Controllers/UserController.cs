using Microsoft.AspNetCore.Mvc;
using RecommendationService.Model;
using RecommendationService.Service.UserServices;

namespace RecommendationService.Controllers
{
    [Route("api/[controller]")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly IUserService _userService;

        public UserController(IUserService userService)
        {
            _userService = userService;
        }

        [HttpPost]
        [Route("{id}")]
        public IActionResult CreateNewUser(string id)
        {
            return Ok(_userService.CreateUser(id));
        }


        [HttpDelete]
        [Route("{id}")]
        public IActionResult DeleteUser(string id)
        {
            _userService.DeleteUser(id);
            return Ok();
        }
    }
}


