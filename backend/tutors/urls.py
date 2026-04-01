from rest_framework.routers import DefaultRouter
from .views import TutorViewSet

router = DefaultRouter()
router.register(r"tutors", TutorViewSet)

urlpatterns = router.urls