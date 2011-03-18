package com.jayway.drawer.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class NetworkRepository extends DrawingRepository {

	private final String baseUrl;
	private final HttpClient httpClient;
	private final Gson gson;

	public NetworkRepository(String baseUrl) {
		this.baseUrl = baseUrl;
		httpClient = new DefaultHttpClient();
		gson = new GsonBuilder().setFieldNamingPolicy(
				FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
	}

	@Override
	public Gallery getGallery() {
		try {
			HttpGet get = new HttpGet(baseUrl + "/drawings");
			HttpResponse response = httpClient.execute(get);
			checkResponseCode(response);
			InputStream in = response.getEntity().getContent();
			InputStreamReader reader = new InputStreamReader(in);
			Gallery gallery = gson.fromJson(reader, Gallery.class);
			return gallery;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void deleteDrawing(Drawing drawing) {
		try {
			HttpDelete delete = new HttpDelete(baseUrl + "/drawings/"
					+ drawing.getId());
			HttpResponse response = httpClient.execute(delete);
			checkResponseCode(response);
		} catch (Exception e) {
			Log.d("Drawer", "Could not delete drawing", e);
		}
	}

	@Override
	public void saveDrawing(Drawing drawing) {
		try {
			HttpPut put = new HttpPut(baseUrl + "/drawings/" + drawing.getId());
			String json = gson.toJson(drawing);
			List<NameValuePair> values = new ArrayList<NameValuePair>();
			values.add(new BasicNameValuePair("drawing", json));
			put.setEntity(new UrlEncodedFormEntity(values));
			HttpResponse response = httpClient.execute(put);
			checkResponseCode(response);
		} catch (Exception e) {
			Log.d("Drawer", "Could not save drawing", e);
		}
	}

	@Override
	public Drawing getDrawing(int id) {
		try {
			HttpGet get = new HttpGet(baseUrl + "/drawings/" + id);
			HttpResponse response = httpClient.execute(get);
			checkResponseCode(response);
			InputStream in = response.getEntity().getContent();
			InputStreamReader reader = new InputStreamReader(in);
			Drawing drawing = gson.fromJson(reader, Drawing.class);
			return drawing;
		} catch (Exception e) {
			Log.d("Drawer", "Could not retrieve drawing", e);
		}
		return null;
	}

	private void checkResponseCode(HttpResponse response) throws IOException {
		StatusLine status = response.getStatusLine();
		if (status.getStatusCode() != HttpStatus.SC_OK)
			throw new IOException("Response code was " + status.getStatusCode());
	}

}
