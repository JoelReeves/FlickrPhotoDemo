package net.joelreeves.flickrphotodemo.services;

import net.joelreeves.flickrphotodemo.data.models.PhotoResponse;
import net.joelreeves.flickrphotodemo.data.services.FlickrService;
import net.joelreeves.flickrphotodemo.data.services.PhotoRepository;

import org.junit.Test;

import retrofit2.Call;

import static com.google.common.truth.Truth.assertThat;
import static net.joelreeves.flickrphotodemo.data.services.PhotoRepository.PhotoRepositoryListener;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

public class PhotoRepositoryTests {

    @Test
    public void testPhotoListShouldBeEmpty() {
        FlickrService mockedFlickrService = mock(FlickrService.class);
        PhotoRepository photoRepository = new PhotoRepository(mockedFlickrService);

        int expectedNumberOfPhotos = 0;
        assertThat(photoRepository.getPhotoList()).hasSize(expectedNumberOfPhotos);
    }

    @Test
    public void testPhotoListShouldHaveExpectedNumberOfPhotos() {
        FlickrService mockedFlickrService = mock(FlickrService.class);
        PhotoRepository photoRepository = new PhotoRepository(mockedFlickrService);
        int expectedNumberOfPhotos = 10;
        photoRepository.fillPhotoList(expectedNumberOfPhotos);

        assertThat(photoRepository.getPhotoList().size()).isEqualTo(expectedNumberOfPhotos);
    }

    @Test
    public void testPhotoListShouldBeEmptyAfterClear() {
        FlickrService mockedFlickrService = mock(FlickrService.class);
        PhotoRepository photoRepository = new PhotoRepository(mockedFlickrService);
        int expectedNumberOfPhotos = 100;
        photoRepository.fillPhotoList(expectedNumberOfPhotos);

        assertThat(photoRepository.getPhotoList().size()).isEqualTo(expectedNumberOfPhotos);

        photoRepository.clear();

        int exptectedNumberOfPhotosAfterClear = 0;
        assertThat(photoRepository.getPhotoList().size()).isEqualTo(exptectedNumberOfPhotosAfterClear);
    }

    @Test
    public void testNullPhotoRepositoryListenerShouldNotBeInvoked() {
        FlickrService mockedFlickrService = mock(FlickrService.class);
        Call<PhotoResponse> mockedPhotoResponse = mock(Call.class);
        PhotoRepository photoRepository = new PhotoRepository(mockedFlickrService);
        PhotoRepositoryListener mockedPhotoRepositoryListener = mock(PhotoRepositoryListener.class);

        when(mockedFlickrService.getRecentPhotos(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(mockedPhotoResponse);
        photoRepository.registerCallback(PhotoRepository.CallbackResults.SUCCESS);

        verifyZeroInteractions(mockedPhotoRepositoryListener);
    }

    @Test
    public void testApiShouldNotifySuccessOnce() {
        FlickrService mockedFlickrService = mock(FlickrService.class);
        Call<PhotoResponse> mockedPhotoResponse = mock(Call.class);
        PhotoRepository photoRepository = new PhotoRepository(mockedFlickrService);
        PhotoRepositoryListener mockedPhotoRepositoryListener = mock(PhotoRepositoryListener.class);
        photoRepository.setPhotoRepositoryListener(mockedPhotoRepositoryListener);

        when(mockedFlickrService.getRecentPhotos(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(mockedPhotoResponse);
        photoRepository.registerCallback(PhotoRepository.CallbackResults.SUCCESS);

        verify(mockedPhotoRepositoryListener, times(1)).onSuccess();
    }

    @Test
    public void testApiShouldNotifyFailureOnce() {
        FlickrService mockedFlickrService = mock(FlickrService.class);
        Call<PhotoResponse> mockedPhotoResponse = mock(Call.class);
        PhotoRepository photoRepository = new PhotoRepository(mockedFlickrService);
        PhotoRepositoryListener mockedPhotoRepositoryListener = mock(PhotoRepositoryListener.class);
        photoRepository.setPhotoRepositoryListener(mockedPhotoRepositoryListener);

        when(mockedFlickrService.getRecentPhotos(anyString(), anyString(), anyString(), anyString(), anyString())).thenReturn(mockedPhotoResponse);
        photoRepository.registerCallback(PhotoRepository.CallbackResults.FAILURE);
        verify(mockedPhotoRepositoryListener, times(1)).onFailure("");
    }
}
