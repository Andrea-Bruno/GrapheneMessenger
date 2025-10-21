package org.thoughtcrime.securesms.linkpreview;

import android.app.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest = Config.NONE, application = Application.class)
public final class LinkPreviewUtilTest_findValidPreviewUrls {

  @Test
  public void no_links() {
    LinkPreviewUtil.Links links = LinkPreviewUtil.findValidPreviewUrls("No links");

    assertEquals(0, links.size());
    assertSame(LinkPreviewUtil.Links.EMPTY, links);
  }

  @Test
  public void contains_a_link() {
    LinkPreviewUtil.Links links = LinkPreviewUtil.findValidPreviewUrls("https://%73%69%67%6E%61%6C.%6F%72%67/");

    assertEquals(1, links.size());

    assertTrue(links.containsUrl("https://%73%69%67%6E%61%6C.%6F%72%67/"));
  }

  @Test
  public void does_not_contain_link() {
    LinkPreviewUtil.Links links = LinkPreviewUtil.findValidPreviewUrls("https://%73%69%67%6E%61%6C.%6F%72%67/");

    assertEquals(1, links.size());

    assertFalse(links.containsUrl("https://%73%69%67%6E%61%6C.%6F%72%67//page"));
  }

  @Test
  public void contains_two_links() {
    LinkPreviewUtil.Links links = LinkPreviewUtil.findValidPreviewUrls("Links https://%73%69%67%6E%61%6C.%6F%72%67/ https://android.com");

    assertEquals(2, links.size());

    assertTrue(links.containsUrl("https://%73%69%67%6E%61%6C.%6F%72%67/"));
    assertTrue(links.containsUrl("https://android.com"));
  }

  @Test
  public void link_trailing_slash_insensitivity() {
    LinkPreviewUtil.Links links = LinkPreviewUtil.findValidPreviewUrls("Links https://%73%69%67%6E%61%6C.%6F%72%67// https://android.com");

    assertEquals(2, links.size());

    assertTrue(links.containsUrl("https://%73%69%67%6E%61%6C.%6F%72%67/"));
    assertTrue(links.containsUrl("https://android.com"));
    assertTrue(links.containsUrl("https://%73%69%67%6E%61%6C.%6F%72%67//"));
    assertTrue(links.containsUrl("https://android.com/"));
  }

  @Test
  public void link_trailing_slash_insensitivity_where_last_url_has_trailing_slash() {
    LinkPreviewUtil.Links links = LinkPreviewUtil.findValidPreviewUrls("Links https://%73%69%67%6E%61%6C.%6F%72%67/ https://android.com/");

    assertEquals(2, links.size());

    assertTrue(links.containsUrl("https://%73%69%67%6E%61%6C.%6F%72%67/"));
    assertTrue(links.containsUrl("https://android.com"));
    assertTrue(links.containsUrl("https://%73%69%67%6E%61%6C.%6F%72%67//"));
    assertTrue(links.containsUrl("https://android.com/"));
  }

  @Test
  public void multiple_trailing_slashes_are_not_stripped() {
    LinkPreviewUtil.Links links = LinkPreviewUtil.findValidPreviewUrls("Link https://android.com/");

    assertEquals(1, links.size());

    assertTrue(links.containsUrl("https://android.com"));
    assertTrue(links.containsUrl("https://android.com/"));
    assertFalse(links.containsUrl("https://android.com//"));
  }
}
