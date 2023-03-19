package com.prof.rssparser.sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.prof.rssparser.sample.common.FeedItem

@Composable
internal fun FeedList(
    padding: PaddingValues = PaddingValues(),
    feedItems: List<FeedItem>,
) {
    LazyColumn(
        modifier = Modifier
            .padding(padding)
    ) {
        items(feedItems) { feedItem ->
            FeedItemView(feedItem = feedItem)
        }
    }
}

@Composable
private fun FeedItemView(
    feedItem: FeedItem,
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(vertical = 8.dp)
    ) {

        Row(
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .weight(1f)
            ) {

                Text(
                    modifier = Modifier
                        .padding(top = 8.dp),
                    text = feedItem.title,
                    style = MaterialTheme.typography.h6,
                )

                feedItem.subtitle?.let { subtitle ->
                    Text(
                        modifier = Modifier
                            .padding(top = 8.dp),
                        text = subtitle,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.body2,
                    )
                }
            }

            feedItem.imageUrl?.let { url ->
                AsyncImage(
                    modifier = Modifier
                        .wrapContentHeight()
                        .width(150.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    load = { loadImageBitmap(url) },
                    painterFor = { remember { BitmapPainter(it) } },
                    contentDescription = null,
                )
            }
        }

        Text(
            modifier = Modifier
                .padding(top = 8.dp),
            text = feedItem.dateString,
            style = MaterialTheme.typography.body2
        )

        Divider(
            modifier = Modifier
                .padding(top = 16.dp),
            thickness = 0.2.dp,
            color = Color.Gray,
        )
    }
}
